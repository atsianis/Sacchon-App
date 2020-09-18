package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
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

public class PatientListImpl extends ServerResource implements PatientList {

    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
    private PatientRepository patientRepository ;


    @Override
    protected void doInit() {
        LOGGER.info("Initialising product resource starts");
        try {
            patientRepository = new PatientRepository (JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        LOGGER.info("Initialising product resource ends");
    }

    public List<PatientRepresentation> getAllPatients() throws NotFoundException {

        LOGGER.finer("Select all patients.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        try{

            List<Patients> patients = patientRepository.findAllPatients();
            List<PatientRepresentation> result = new ArrayList<>();

            for (Patients patient : patients)
                if(!patient.isDeleted())
                    result.add(new PatientRepresentation(patient));
            //patients.forEach(patient -> result.add(new PatientRepresentation(patient)));
            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("patients not found");
        }
    }

    @Override
    public PatientRepresentation add (PatientRepresentation patientRepresentation)
            throws BadEntityException {

        LOGGER.finer("Add a new patient.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to add a patient.");
        // Check entity
        ResourceValidator.notNull(patientRepresentation);
        ResourceValidator.validate(patientRepresentation);
        LOGGER.finer("Patient checked");

        try {
            // Convert PatientRepr to Patient
            Patients patientsIn = new Patients();
            patientsIn.setFirstName(patientRepresentation.getFirstName());
            patientsIn.setLastName(patientRepresentation.getLastName());
            patientsIn.setEmail(patientRepresentation.getEmail());
            patientsIn.setPassword(patientRepresentation.getPassword());
            patientsIn.setDob(patientRepresentation.getDob());


            Optional<Patients> patientOut = patientRepository.save(patientsIn);
            Patients patients = null;
            if (patientOut.isPresent())
                patients = patientOut.get();
            else
                throw new BadEntityException(" Patient has not been created");

            PatientRepresentation result = new PatientRepresentation();
            result.setFirstName(patients.getFirstName());
            result.setLastName(patients.getLastName());
            result.setEmail(patients.getEmail());
            result.setPassword(patients.getPassword());
            result.setDob(patients.getDob());
            result.setUri("http://localhost:9000/v1/patient/"+ patients.getId());

            getResponse().setLocationRef("http://localhost:9000/v1/patient/"+ patients.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("Patient successfully added.");
            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a patient", ex);
            throw new ResourceException(ex);
        }
    }

    @Override
    public List<PatientRepresentation> getAllAvailablePatients() throws NotFoundException {
        LOGGER.finer("Select available patients.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        try{

            List<Patients> patients = patientRepository.findAllPatients();
            List<PatientRepresentation> result = new ArrayList<>();

            for (Patients patient : patients){
                if(patient.isCanBeExamined() == true)
                    result.add(new PatientRepresentation(patient));
            }
            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("patients not found");
        }
    }
}
