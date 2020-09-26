package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRecordRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientRecordsListImpl extends ServerResource implements PatientRecordsList {
    public static final Logger LOGGER = Engine.getLogger(PatientRecordsListImpl.class);
    private PatientRecordRepository patientRecordRepository;
    private PatientRepository patientRepository;
    long id;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient record resource L starts");
        try {
            patientRecordRepository = new PatientRecordRepository(JpaUtil.getEntityManager());
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient record resource L ends");
    }

    @Override
    public List<PatientRecordRepresentation> getAllPatientRecords() throws NotFoundException {
        LOGGER.finer("Select all records.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        try{
            List<PatientRecords> patientRecords = patientRecordRepository.findAllPatientRecords();
            List<PatientRecordRepresentation> result = new ArrayList<>();

            for(PatientRecords p : patientRecords)
                result.add(new PatientRecordRepresentation(p));

            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("patients not found");
        }
    }

    @Override
    public PatientRecordRepresentation storeData(PatientRecordRepresentation patientRecordRepresentation) throws NotFoundException, BadEntityException {
        LOGGER.finer("Add a new record.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to add a record.");
        // get patient
        Optional<Patients> opatient = patientRepository.findById(id);
        if (opatient.isPresent()){
            // Check entity
            // Convert to PatientRepr so a validation can procceed
            Patients patient = opatient.get();
            PatientRepresentation pr = new PatientRepresentation();
            pr.setLastName(patient.getLastName());
            pr.setFirstName(patient.getFirstName());

            ResourceValidator.notNull(patientRecordRepresentation);
            ResourceValidator.validatePatient(pr);
            LOGGER.finer("Patient checked");

            try {
                // Convert PatientRecordRepr to PatientRecord
                PatientRecords patientRecordsIn = new PatientRecords();
                patientRecordsIn.setSacchon(patientRecordRepresentation.getSacchon());
                patientRecordsIn.setCalories(patientRecordRepresentation.getCalories());
                patientRecordsIn.setTimeCreated(patientRecordRepresentation.getTimeCreated());
                patientRecordsIn.setPatient(patient);

                // Check if patient can add PatientRecord
                boolean checkDate = patientRepository.check(patientRecordsIn);

                if(!checkDate) {
                    Optional<PatientRecords> patientRecordsOut = patientRecordRepository.save(patientRecordsIn);
                    PatientRecords patientRecords = null;
                    if(patientRecordsOut.isPresent())
                        patientRecords = patientRecordsOut.get();
                    else
                        throw new BadEntityException("Record has not been created");

                    //Convert PatientRecord to PatientRecordRepr
                    PatientRecordRepresentation result = new PatientRecordRepresentation();
                    result.setSacchon(patientRecords.getSacchon());
                    result.setCalories(patientRecords.getCalories());
                    result.setTimeCreated(patientRecords.getTimeCreated());
                    result.setId(patientRecords.getId());

                    getResponse().setLocationRef("http://localhost:9000/v1/patient/"+patient.getId()+"/storeData/patientRecord/"+patientRecords.getId());
                    getResponse().setStatus(Status.SUCCESS_CREATED);

                    LOGGER.finer("Record successfully added.");
                    return result;
                } else {
                    return null;
                }

            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error when adding a Record", ex);
                throw new ResourceException(ex);
            }
        }else{
            throw new NotFoundException("Patient not found");
        }
    }
}
