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
import org.restlet.data.Status;
import org.restlet.engine.Engine;
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
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
            consultationRepresentation = new ConsultationRepresentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public ResponseRepresentation<PatientRepresentation> add(CreatedOrUpdatedPatientRepresentation patientRepresentation) {
        LOGGER.info("Add a new patient.");
        // Check entity
        try {
            ResourceValidator.notNull(patientRepresentation);
            ResourceValidator.validate(patientRepresentation);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }

        LOGGER.info("Patient checked");

        try {
            // Convert PatientRepr to Patient
            Patients patientsIn = new Patients();
            patientsIn.setFirstName(patientRepresentation.getFirstName());
            patientsIn.setLastName(patientRepresentation.getLastName());
            patientsIn.setEmail(patientRepresentation.getEmail());
            patientsIn.setPassword(patientRepresentation.getPassword());
            patientsIn.setDob(patientRepresentation.getDob());
            patientsIn.setGender(patientRepresentation.getGender());

            return getPatientRepresentationResponseRepresentation(patientsIn);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a patient", ex);
            return new ResponseRepresentation<>(404, "Patient not found", null);
        }
    }

    @NotNull
    private ResponseRepresentation<PatientRepresentation> getPatientRepresentationResponseRepresentation(Patients patientsIn) {
        try {
            Optional<Patients> patientOut = patientRepository.save(patientsIn);
            // this savedPatient has ID now
            Patients savedPatient = patientOut.get();
            // Create First Consultation which is NULL
            Consultations consultation = consultationRepresentation.createConsultation();
            // To make the consultation-patient relationship work, the setted patient must be persisted
            consultation.setPatient(savedPatient);
            consultation.setComment(null);
            consultation.setSeenByPatient(new Date());
            consultation.setTimeCreated(new Date());
            // Add consult in db
            consultationRepository.save(consultation);

            Patients patients = null;
            if (patientOut.isPresent())
                patients = patientOut.get();
            else
                return new ResponseRepresentation<>(404, "Patient not found", null);

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

            return new ResponseRepresentation<>(200, "Patient registered successfully", result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }
    }
}
