package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRecordRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.jetbrains.annotations.NotNull;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PatientRecordsListImpl extends ServerResource implements PatientRecordsList {
    public static final Logger LOGGER = Engine.getLogger(PatientRecordsListImpl.class);
    private PatientRecordRepository patientRecordRepository;
    private PatientRepository patientRepository;
    long patient_id;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient record resource L starts");
        try {
            patientRecordRepository = new PatientRecordRepository(em);
            patientRepository = new PatientRepository(em);
            patient_id = Long.parseLong(getAttribute("patient_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient record resource L ends");
    }

    @Override
    public ResponseRepresentation<List<PatientRecordRepresentation>> getAllPatientRecords() {
        LOGGER.finer("Select all records.");
        try {
            List<PatientRecords> patientRecords = patientRecordRepository.findPatientRecordsByPatient(patient_id);
            List<PatientRecordRepresentation> result = new ArrayList<>();

            for (PatientRecords p : patientRecords)
                result.add(new PatientRecordRepresentation(p));

            return new ResponseRepresentation<>(200, "Records retrieved", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Records not found", null);
        }
    }

    @Override
    public ResponseRepresentation<PatientRecordRepresentation> storeData(PatientRecordRepresentation patientRecordRepresentation) {
        LOGGER.finer("Add a new record.");
        if (patientRecordRepresentation.getCarbs() == 0 || patientRecordRepresentation.getGlycose() == 0)
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        // get patient
        Optional<Patients> optionalPatient = patientRepository.findById(patient_id);
        if (optionalPatient.isPresent()) {
            // Check entity
            // Convert to PatientRepresentation and validate
            Patients patient = optionalPatient.get();
            try {
                ResourceValidator.notNull(patientRecordRepresentation);
                ResourceValidator.validate(setPatientRepresentation(patient));
            } catch (BadEntityException ex) {
                return new ResponseRepresentation<>(422, "Bad Entity", null);
            }

            LOGGER.finer("Patient checked");

            try {
                PatientRecords patientRecordsIn = getPatientRecordsIn(patientRecordRepresentation, patient);

                long lastConsultationInDays = patientRepository.lastConsultationInDays(patient.getConsultations());
                boolean lastConsultationLessThanAMonthAgo = true;
                if (patient.getConsultations().size() != 0)
                    lastConsultationLessThanAMonthAgo = (lastConsultationInDays <= 30);
                boolean recordTimeMoreRecentThanPatientsCreationTime = patientRepository.checkPatientsCreationTime(patientRecordsIn, patient.getTimeCreated());

                if (lastConsultationLessThanAMonthAgo && recordTimeMoreRecentThanPatientsCreationTime) {

                    Optional<PatientRecords> patientRecordOut = patientRecordRepository.save(patientRecordsIn);
                    PatientRecords patientRecord;
                    if (patientRecordOut.isPresent()) {
                        patientRecord = patientRecordOut.get();
                        updatePatientActivity(patient);
                    } else
                        return new ResponseRepresentation<>(404, "Record not found", null);

                    PatientRecordRepresentation result = getPatientRecordRepresentationOut(patientRecord);
                    LOGGER.finer("Record successfully added.");

                    if (lastConsultationInDays == 30) {
                        try {
                            patientRepository.updateCanBeExamined(patient);
                        } catch (SQLException ex) {
                            return new ResponseRepresentation<>(200, "Record created but could not update patient state", result);
                        }
                    }

                    return new ResponseRepresentation<>(200, "Record created", result);
                } else {
                    return new ResponseRepresentation<>(422, "Cannot insert Record right now." +
                            "Maybe you have a consultation pending !", null);
                }
            } catch (Exception ex) {
                return new ResponseRepresentation<>(422, "Could not upload data", null);
            }
        } else {
            return new ResponseRepresentation<>(404, "Not found", null);
        }
    }

    /**
     * @param patient Updates the Patient's field 'lastActive' in the database
     */
    @NotNull
    private void updatePatientActivity(Patients patient) {
        patient.setLastActive(new Date());
        patientRepository.update(patient);
    }

    /**
     * @param patient
     * @return PatientRepresentation
     * <p>
     * Convert the Patient into a PatientRepresentation in order to validate his fields
     */
    @NotNull
    private PatientRepresentation setPatientRepresentation(Patients patient) {
        PatientRepresentation pr = new PatientRepresentation();
        pr.setLastName(patient.getLastName());
        pr.setFirstName(patient.getFirstName());
        return pr;
    }

    /**
     * @param patientRecord
     * @return PatientRecordRepresentation object
     * <p>
     * Convert the persisted PatientRecord to the PatientRecordRepresentation
     * that will be returned to the user
     */
    @NotNull
    private PatientRecordRepresentation getPatientRecordRepresentationOut(PatientRecords patientRecord) {
        PatientRecordRepresentation result = new PatientRecordRepresentation();
        result.setGlycose(patientRecord.getGlycose());
        result.setCarbs(patientRecord.getCarbs());
        result.setTimeCreated(patientRecord.getTimeCreated());
        result.setId(patientRecord.getId());
        return result;
    }

    /**
     * @param patientRecordRepresentation
     * @param patient
     * @return PatientRecord
     * <p>
     * Converts the input of the user into a PatientRecord object
     * that is ready to be persisted
     */
    @NotNull
    private PatientRecords getPatientRecordsIn(PatientRecordRepresentation patientRecordRepresentation, Patients patient) {
        PatientRecords patientRecordsIn = new PatientRecords();
        patientRecordsIn.setGlycose(patientRecordRepresentation.getGlycose());
        patientRecordsIn.setCarbs(patientRecordRepresentation.getCarbs());
        patientRecordsIn.setTimeCreated(new Date());
        patientRecordsIn.setPatient(patient);
        return patientRecordsIn;
    }
}
