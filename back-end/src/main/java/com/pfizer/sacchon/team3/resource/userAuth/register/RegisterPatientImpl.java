package com.pfizer.sacchon.team3.resource.userAuth.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedPatientRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.jetbrains.annotations.NotNull;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterPatientImpl extends ServerResource implements RegisterPatient {
    public static final Logger LOGGER = Engine.getLogger(RegisterPatientImpl.class);
    private PatientRepository patientRepository;
    private ConsultationRepository consultationRepository;
    private ConsultationRepresentation consultationRepresentation;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(em);
            consultationRepository = new ConsultationRepository(em);
            consultationRepresentation = new ConsultationRepresentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public ResponseRepresentation<PatientRepresentation> registerPatient(CreatedOrUpdatedPatientRepresentation patientRepresentation) {
        LOGGER.info("Add a new patient.");
        try {
            ResourceValidator.notNull(patientRepresentation);
            ResourceValidator.validate(patientRepresentation);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }

        LOGGER.info("Patient checked");

        try {
            Patients patientsIn = getToBePersistedPatient(patientRepresentation);

            return patientRegistrationAttempt(patientsIn);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a patient", ex);

            return new ResponseRepresentation<>(404, "Patient not found", null);
        }
    }

    /**
     * @param patientRepresentation
     * @return a Patient Entity
     * <p>
     * convert the PatientRepresentation input into the Patient entity
     * that will be attempted to be persisted into the Database
     */
    @NotNull
    private Patients getToBePersistedPatient(CreatedOrUpdatedPatientRepresentation patientRepresentation) {
        Patients patientsIn = new Patients();
        patientsIn.setFirstName(patientRepresentation.getFirstName());
        patientsIn.setLastName(patientRepresentation.getLastName());
        patientsIn.setEmail(patientRepresentation.getEmail());
        patientsIn.setPassword(patientRepresentation.getPassword());
        patientsIn.setDob(patientRepresentation.getDob());
        patientsIn.setLastActive(new Date());
        patientsIn.setGender(patientRepresentation.getGender());
        patientsIn.setTimeCreated(new Date());

        return patientsIn;
    }

    /**
     * @param patientsIn
     * @return a PatientRepresentation
     * <p>
     * Attempting to persist the Patient into the Database
     * In case of success, a PatientRepresentation of the persisted entity is returned,
     * Otherwise, the method will return null
     */
    @NotNull
    private ResponseRepresentation<PatientRepresentation> patientRegistrationAttempt(Patients patientsIn) {
        try {
            Optional<Patients> patientOut = patientRepository.save(patientsIn);
            Patients savedPatient = patientOut.get();
            createNullConsultation(savedPatient);

            Patients patients;
            if (patientOut.isPresent())
                patients = patientOut.get();
            else
                return new ResponseRepresentation<>(404, "Patient not found", null);

            PatientRepresentation result = getPatientRepresentationOut(patients);

            LOGGER.finer("Patient successfully added.");

            return new ResponseRepresentation<>(200, "Patient registered successfully", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }
    }

    /**
     * @param savedPatient When a new patient enters the system, a no-comment,no-doctor consultation with the date of
     *                     his/her creation is persisted in the Database.
     *                     This first consultation is used as a landmark for the creation date
     *                     of his first actual consultation by a doctor.
     *                     <p>
     *                     See main/java/com/pfizer/sacchon/team3/resource/consultation/AddConsultationImpl.java
     *                     for the implementation of the consultation creation
     */
    private void createNullConsultation(Patients savedPatient) {
        Consultations consultation = consultationRepresentation.createConsultation();
        consultation.setPatient(savedPatient);
        consultation.setComment(null);
        consultation.setSeenByPatient(null);
        consultation.setTimeCreated(new Date());

        consultationRepository.save(consultation);
    }

    /**
     * @param patients
     * @return PatientRepresentation
     * <p>
     * converts the persisted patient to a PatientRepresentation type object
     * that will be returned to the client
     */
    @NotNull
    private PatientRepresentation getPatientRepresentationOut(Patients patients) {
        PatientRepresentation result = new PatientRepresentation();
        result.setFirstName(patients.getFirstName());
        result.setLastName(patients.getLastName());
        result.setEmail(patients.getEmail());
        result.setPassword(patients.getPassword());
        result.setDob(patients.getDob());
        result.setId(patients.getId());

        return result;
    }
}
