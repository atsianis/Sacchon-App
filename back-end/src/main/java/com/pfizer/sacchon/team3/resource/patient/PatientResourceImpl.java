package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
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
    public ResponseRepresentation<PatientRepresentation> getPatient(){
        LOGGER.info("Retrieve a patient");
        // Initialize the persistence layer.
        PatientRepository patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        Patients patient;
        try {
            Optional<Patients> opProduct = patientRepository.findById(id);
            setExisting(opProduct.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + id);
                return new ResponseRepresentation<PatientRepresentation>(404,"Patient not found",null);
            } else {
                patient = opProduct.get();
                Hibernate.initialize(patient.getPatientRecords());
                Hibernate.initialize(patient.getConsultations());
                LOGGER.finer("User allowed to retrieve a product.");
                PatientRepresentation result = new PatientRepresentation(patient);
                LOGGER.finer("Patient successfully retrieved");

                return new ResponseRepresentation<PatientRepresentation>(200,"Patient retrieved",result);
            }
        } catch (Exception ex) {
            return new ResponseRepresentation<PatientRepresentation>(404,"Patient not found",null);
        }
    }

    @Override
    public ResponseRepresentation<PatientRepresentation> remove(){
        LOGGER.finer("Removal of patient");
        try {
            // Delete patient in DB: return true if deleted
            Boolean isDeleted = patientRepository.remove(id);
            // If patient has not been deleted: if not it means that the id must
            // be wrong
            if (!isDeleted) {
                LOGGER.config("Patient id does not exist");
                return new ResponseRepresentation<PatientRepresentation>(404,"Patient not found",null);
            }
            LOGGER.finer("Patient successfully removed.");
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a patient", ex);
            return new ResponseRepresentation<PatientRepresentation>(404,"Patient not found",null);
        }
        return new ResponseRepresentation<PatientRepresentation>(200,"Patient removed",null);
    }

    @Override
    public ResponseRepresentation<PatientRepresentation> store(PatientRepresentation patientRepresentation){
        LOGGER.finer("Update a patient.");
        // Check given entity
        try{
            ResourceValidator.notNull(patientRepresentation);
            ResourceValidator.validatePatient(patientRepresentation);
        }catch(BadEntityException ex){
            return new ResponseRepresentation<PatientRepresentation>(422,"Bad Entity",null);
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
                    return new ResponseRepresentation<PatientRepresentation>(404,"Patient not found",null);
                }
            } else {
                LOGGER.finer("Patient does not exist.");
                return new ResponseRepresentation<PatientRepresentation>(404,"Patient not found",null);
            }
            LOGGER.finer("Patient successfully updated.");
            return new ResponseRepresentation<PatientRepresentation>(200,"Patient created",new PatientRepresentation(patientOut.get()));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
