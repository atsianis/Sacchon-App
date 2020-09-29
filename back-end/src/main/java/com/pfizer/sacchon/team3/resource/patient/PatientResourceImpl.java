package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.resource.util.Validate;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class PatientResourceImpl extends ServerResource implements PatientResource {
    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
    private long id;
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            id = -1;
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public ResponseRepresentation<PatientRepresentation> getPatient() {
        LOGGER.info("Retrieve a patient");
        // Initialize the persistence layer.
        patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        Patients patient;
        try {
            Optional<Patients> opProduct = patientRepository.findById(id);
            setExisting(opProduct.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + id);
                return new ResponseRepresentation<>(404, "Patient not found", null);
            } else {
                patient = opProduct.get();
                Hibernate.initialize(patient.getPatientRecords());
                Hibernate.initialize(patient.getConsultations());
                LOGGER.finer("User allowed to retrieve a product.");
                PatientRepresentation result = new PatientRepresentation(patient);
                LOGGER.finer("Patient successfully retrieved");

                return new ResponseRepresentation<>(200, "Patient retrieved", result);
            }
        } catch (Exception ex) {
            return new ResponseRepresentation<>(404, "Patient not found", null);
        }
    }

    @Override
    public ResponseRepresentation<PatientRepresentation> updatePatient(PatientRepresentation patientRepresentation) {
        LOGGER.finer("Update a patient.");
        // Check given entity
        try {
                ResourceValidator.notNull(patientRepresentation);
                ResourceValidator.validate(patientRepresentation);
            } catch (BadEntityException ex) {
                return new ResponseRepresentation<>(422, "Bad Entity", null);
            }

        LOGGER.finer("Patient checked");
        try {
            // Convert PatientRepr to Patient
            Patients patientsIn = patientRepresentation.createPatient();
            patientsIn.setId(id);
            Optional<Patients> patientOut = patientRepository.findById(id);
            setExisting(patientOut.isPresent());

            // If patient exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update patient.");
                // Update patient in DB and retrieve the new one.
                patientOut = patientRepository.update(patientsIn);
                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!patientOut.isPresent()) {
                    LOGGER.finer("Patient does not exist.");
                    return new ResponseRepresentation<>(404, "Patient not found", null);
                }
            } else {
                LOGGER.finer("Patient does not exist.");
                return new ResponseRepresentation<>(404, "Patient not found", null);
            }
            LOGGER.finer("Patient successfully updated.");
            return new ResponseRepresentation<>(200, "Patient created", new PatientRepresentation(patientOut.get()));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
