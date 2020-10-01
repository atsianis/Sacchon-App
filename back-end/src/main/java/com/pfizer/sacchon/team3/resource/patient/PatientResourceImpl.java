package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class PatientResourceImpl extends ServerResource implements PatientResource {
    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
    private long patient_id;
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            patient_id = Long.parseLong(getAttribute("patient_id"));
        } catch (Exception e) {
            patient_id = -1;
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
            Optional<Patients> opProduct = patientRepository.findById(patient_id);
            setExisting(opProduct.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + patient_id);
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
            } catch (BadEntityException ex) {
                return new ResponseRepresentation<>(422, "Bad Entity", null);
            }

        LOGGER.finer("Patient checked");
        try {
            Optional<Patients> patientOut = patientRepository.findById(patient_id);
            setExisting(patientOut.isPresent());
            Patients patientToBePersisted ;
            // If patient exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update patient.");
                patientToBePersisted = getPatientToBePersisted(patientRepresentation, patientOut);
                // Update patient in DB and retrieve the new one.
                patientOut = patientRepository.update(patientToBePersisted);
                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!patientOut.isPresent()) {
                    LOGGER.finer("Patient does not exist.");
                    return new ResponseRepresentation<>(404, "SQL Exception", null);
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

    @NotNull
    private Patients getPatientToBePersisted(PatientRepresentation patientRepresentation, Optional<Patients> patientOut) {
        Patients patient = patientOut.get();
        if (!(patientRepresentation.getDob()==null))
            patient.setDob(patientRepresentation.getDob());
        if (!(patientRepresentation.getPassword()==null))
            patient.setPassword((patientRepresentation.getPassword()));
        if (!(patientRepresentation.getFirstName()==null))
            patient.setFirstName(patientRepresentation.getFirstName());
        if (!(patientRepresentation.getLastName()==null))
            patient.setLastName(patientRepresentation.getLastName());
        if (!(patientRepresentation.getEmail()==null))
            patient.setEmail(patientRepresentation.getEmail());

        return patient;
    }
}
