package com.pfizer.sacchon.team3.resource.patientRecord;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRecordRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.patient.PatientResourceImpl;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientRecordResourceImpl extends ServerResource implements PatientRecordResource {
    public static final Logger LOGGER = Engine.getLogger(PatientRecordResourceImpl.class);
    private long patient_id;
    private long record_id;
    private PatientRecordRepository patientRecordRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRecordRepository = new PatientRecordRepository(JpaUtil.getEntityManager());
            patient_id = Long.parseLong(getAttribute("pid"));
            record_id = Long.parseLong(getAttribute("rid"));
        } catch (Exception e) {
            patient_id = -1;
            record_id = -1;
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public PatientRecordRepresentation getRecord() throws NotFoundException {
        LOGGER.info("Retrieve a Record");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        // Initialize the persistence layer.
        PatientRecordRepository patientRecordRepository = new PatientRecordRepository(JpaUtil.getEntityManager());
        PatientRecords patientRecords;
        try {
            Optional<PatientRecords> opPatientRec = patientRecordRepository.findById(record_id);
            setExisting(opPatientRec.isPresent());
            if (!isExisting()) {
                LOGGER.config("record id does not exist:" + record_id);
                throw new NotFoundException("No record with  : " + record_id);
            } else {
                patientRecords = opPatientRec.get();
                LOGGER.finer("User allowed to retrieve a record.");
                PatientRecordRepresentation result = new PatientRecordRepresentation(patientRecords);
                LOGGER.finer("Record successfully retrieved");

                return result;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    @Override
    public void remove() throws NotFoundException {
        LOGGER.finer("Removal of record");
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to remove a record.");
        try {
            // Delete record in DB: return true if deleted
            Boolean isDeleted = patientRecordRepository.remove(record_id);
            // If record has not been deleted: if not it means that the id must
            // be wrong
            if (!isDeleted) {
                LOGGER.config("Record id does not exist");
                throw new NotFoundException("Record with the following identifier does not exist:" + record_id);
            }
            LOGGER.finer("Record successfully removed.");
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a record", ex);
            throw new ResourceException(ex);
        }
    }

    @Override
    public PatientRecordRepresentation updateRecord(PatientRecordRepresentation patientRecordRepresentation) throws NotFoundException, BadEntityException {
        LOGGER.finer("Update a record.");

        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to update a record.");
        // Check given entity
        // Convert to PatientRepr so a validation can procceed

        ResourceValidator.notNull(patientRecordRepresentation);
        LOGGER.finer("Patient checked");
        try {
            // Convert PatientRecordRepr to PatientRecord
            PatientRecords patientRecordsIn = patientRecordRepresentation.createPatientRecords();
            patientRecordsIn.setId(record_id);
            Optional<PatientRecords> patientRecordsOut = patientRecordRepository.findById(record_id);
            setExisting(patientRecordsOut.isPresent());
            // If patientRecord exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update record.");
                // Update Record in DB and retrieve the new one.
                patientRecordsOut = patientRecordRepository.update(patientRecordsIn);
                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!patientRecordsOut.isPresent()) {
                    LOGGER.finer("Record does not exist.");
                    throw new NotFoundException("Record with the following id does not exist: " + record_id);
                }
            } else {
                LOGGER.finer("Record does not exist.");
                throw new NotFoundException("Record with the following id does not exist: " + record_id);
            }
            LOGGER.finer("Record successfully updated.");

            return new PatientRecordRepresentation(patientRecordsOut.get());
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
