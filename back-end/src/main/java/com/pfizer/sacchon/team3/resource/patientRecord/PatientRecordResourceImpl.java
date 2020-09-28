package com.pfizer.sacchon.team3.resource.patientRecord;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.repository.PatientRecordRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.restlet.engine.Engine;
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
    public ResponseRepresentation<PatientRecordRepresentation> getRecord() {
        LOGGER.info("Retrieve a Record");
        // Initialize the persistence layer.
        patientRecordRepository = new PatientRecordRepository(JpaUtil.getEntityManager());
        PatientRecords patientRecords;
        try {
            Optional<PatientRecords> opPatientRec = patientRecordRepository.findById(record_id);
            setExisting(opPatientRec.isPresent());
            if (!isExisting()) {
                LOGGER.config("record id does not exist:" + record_id);
                return new ResponseRepresentation<>(404, "Record not found", null);
            } else {
                patientRecords = opPatientRec.get();
                LOGGER.finer("User allowed to retrieve a record.");
                PatientRecordRepresentation result = new PatientRecordRepresentation(patientRecords);
                LOGGER.finer("Record successfully retrieved");

                return new ResponseRepresentation<>(200, "Record found", result);
            }
        } catch (Exception ex) {
            return new ResponseRepresentation<>(404, "Record not found", null);
        }
    }

    @Override
    public ResponseRepresentation<PatientRecordRepresentation> remove() {
        LOGGER.finer("Removal of record");
        try {
            // Delete record in DB: return true if deleted
            Boolean isDeleted = patientRecordRepository.remove(record_id);
            // If record has not been deleted: if not it means that the id must
            // be wrong
            if (!isDeleted) {
                LOGGER.config("Record id does not exist");
                return new ResponseRepresentation<>(404, "Record not found", null);
            }
            LOGGER.finer("Record successfully removed.");
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a record", ex);
            return new ResponseRepresentation<>(404, "Record not found", null);
        }

        return new ResponseRepresentation<>(200, "Record deleted", null);
    }

    @Override
    public ResponseRepresentation<PatientRecordRepresentation> updateRecord(PatientRecordRepresentation patientRecordRepresentation) {
        LOGGER.finer("Update a record.");
        // Check given entity
        try {
            ResourceValidator.notNull(patientRecordRepresentation);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }

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
                    return new ResponseRepresentation<>(404, "Record not found", null);
                }
            } else {
                LOGGER.finer("Record does not exist.");
                return new ResponseRepresentation<>(404, "Record not found", null);
            }
            LOGGER.finer("Record successfully updated.");

            return new ResponseRepresentation<>(200, "Record updated", new PatientRecordRepresentation(patientRecordsOut.get()));
        } catch (Exception ex) {
            return new ResponseRepresentation<>(404, "Record not found", null);
        }
    }
}
