package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
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
    public PatientRepresentation getPatient() throws NotFoundException {
        LOGGER.info("Retrieve a patient");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        // Initialize the persistence layer.
        PatientRepository patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        Patients patient;
        try {
            Optional<Patients> opProduct = patientRepository.findById(id);
            setExisting(opProduct.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + id);
                throw new NotFoundException("No patient with  : " + id);
            } else {
                patient = opProduct.get();
                Hibernate.initialize(patient.getPatientRecords());
                LOGGER.finer("User allowed to retrieve a product.");
                PatientRepresentation result = new PatientRepresentation(patient);
                LOGGER.finer("Patient successfully retrieved");

                return result;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    @Override
    public void remove() throws NotFoundException {
        LOGGER.finer("Removal of patient");
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to remove a patient.");
        try {
            // Delete patient in DB: return true if deleted
            Boolean isDeleted = patientRepository.remove(id);
            // If patient has not been deleted: if not it means that the id must
            // be wrong
            if (!isDeleted) {
                LOGGER.config("Patient id does not exist");
                throw new NotFoundException("Patient with the following identifier does not exist:" + id);
            }
            LOGGER.finer("Patient successfully removed.");
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a patient", ex);
            throw new ResourceException(ex);
        }
    }

    @Override
    public PatientRepresentation store(PatientRepresentation patientRepresentation) throws NotFoundException, BadEntityException {
        LOGGER.finer("Update a patient.");
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to update a patient.");
        // Check given entity
        ResourceValidator.notNull(patientRepresentation);
        ResourceValidator.validatePatient(patientRepresentation);
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
                    throw new NotFoundException("Patient with the following id does not exist: " + id);
                }
            } else {
                LOGGER.finer("Patient does not exist.");
                throw new NotFoundException("Patient with the following id does not exist: " + id);
            }
            LOGGER.finer("Patient successfully updated.");

            return new PatientRepresentation(patientOut.get());
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    @Override
    public PatientRepresentation softDelete(PatientRepresentation patientRepresentation) throws NotFoundException, BadEntityException {
        LOGGER.finer("Soft Delete a patient.");
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to soft Delete a patient.");
        // Check given entity
        ResourceValidator.notNull(patientRepresentation);
        ResourceValidator.validatePatient(patientRepresentation);
        LOGGER.finer("Patient checked");

        try {
            // Convert PatientRepr to Patient
            Patients patientsIn = patientRepresentation.createPatient();
            patientsIn.setId(id);
            Optional<Patients> patientOut = patientRepository.findById(id);
            setExisting(patientOut.isPresent());
            // If patient exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Soft delete Patient.");
                patientOut = patientRepository.softDelete(patientsIn);
                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!patientOut.isPresent()) {
                    LOGGER.finer("Patient does not exist.");
                    throw new NotFoundException("Patient with the following id does not exist: " + id);
                }
            } else {
                LOGGER.finer("Patient does not exist.");
                throw new NotFoundException("Patient with the following id does not exist: " + id);
            }
            LOGGER.finer("Patient successfully updated.");

            return new PatientRepresentation(patientOut.get());
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
