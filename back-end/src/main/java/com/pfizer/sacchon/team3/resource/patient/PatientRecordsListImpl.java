package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRecordRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.patientRecord.PatientRecordResourceImpl;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientRecordsListImpl extends ServerResource implements PatientRecordsList {


    public static final Logger LOGGER = Engine.getLogger(PatientRecordResourceImpl.class);
    private PatientRecordRepository patientRecordRepository;


    @Override
    protected void doInit() {
        LOGGER.info("Initialising product record resource L starts");
        try {
            patientRecordRepository = new PatientRecordRepository(JpaUtil.getEntityManager());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising product record resource L ends");
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
    public PatientRecordRepresentation storeData(PatientRecordRepresentation patientRecordRepresentation, Patients patient) throws NotFoundException, BadEntityException {
       // den kserw an doulevei etsi me ton Patient , mporei na thelei ResourceImpl h Repository
        LOGGER.finer("Add a new record.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to add a record.");
        // Check entity
        // Convert to PatientRepr so a validation can procceed
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
            //patientRecordsIn.setPatients(patient);

            Optional<PatientRecords> patientRecordsOut = patientRecordRepository.save(patientRecordsIn);
            PatientRecords patientRecords = null;
            if(patientRecordsOut.isPresent())
                patientRecords = patientRecordsOut.get();
            else
                throw new BadEntityException("Record has not been created");

            PatientRecordRepresentation result = new PatientRecordRepresentation();
            result.setSacchon(patientRecords.getSacchon());
            result.setCalories(patientRecords.getCalories());
            result.setTimeCreated(patientRecords.getTimeCreated());
            result.setUri("/patient/"+patient.getId()+"/storeData/patientRecord/"+patientRecords.getId());

            getResponse().setLocationRef("http://localhost:9000/v1/patient/"+patient.getId()+"/storeData/patientRecord/"+patientRecords.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("Record successfully added.");
            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a Record", ex);
            throw new ResourceException(ex);
        }
    }
}
