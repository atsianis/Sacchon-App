package com.pfizer.sacchon.team3.resource.userAuth.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterPatientImpl extends ServerResource implements RegisterPatient {
    public static final Logger LOGGER = Engine.getLogger(RegisterPatientImpl.class);
    private PatientRepository patientRepository;
    private ConsultationRepository consultationRepository;
    private ConsultationRepresentation consultationRepresentation;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            consultationRepresentation = new ConsultationRepresentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public PatientRepresentation add(PatientRepresentation patientRepresentation) throws BadEntityException {
        LOGGER.finer("Add a new patient.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to add a patient.");
        // Check entity
        ResourceValidator.notNull(patientRepresentation);
        ResourceValidator.validatePatient(patientRepresentation);
        LOGGER.finer("Patient checked");

        try {
            // Convert PatientRepr to Patient
            Patients patientsIn = new Patients();
            patientsIn.setFirstName(patientRepresentation.getFirstName());
            patientsIn.setLastName(patientRepresentation.getLastName());
            patientsIn.setEmail(patientRepresentation.getEmail());
            patientsIn.setPassword(patientRepresentation.getPassword());
            patientsIn.setDob(patientRepresentation.getDob());
            patientsIn.setGender(patientRepresentation.getGender());

            // Create First Consultation which is NULL
            Consultations consultation = consultationRepresentation.createConsultation();
            consultation.setPatient(patientsIn);
            consultation.setTimeCreated(new Date());
            // Add consult in db
            consultationRepository.save(consultation);

            Optional<Patients> patientOut = patientRepository.save(patientsIn);
            Patients patients = null;
            if (patientOut.isPresent())
                patients = patientOut.get();
            else
                throw new BadEntityException(" Patient has not been created");

            PatientRepresentation result = new PatientRepresentation();
            result.setFirstName(patients.getFirstName());
            result.setLastName(patients.getLastName());
            result.setEmail(patients.getEmail());
            result.setPassword(patients.getPassword());
            result.setDob(patients.getDob());
            result.setId(patients.getId());

            getResponse().setLocationRef("http://localhost:9000/v1/patient/" + patients.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("Patient successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a patient", ex);
            throw new ResourceException(ex);
        }
    }
}
