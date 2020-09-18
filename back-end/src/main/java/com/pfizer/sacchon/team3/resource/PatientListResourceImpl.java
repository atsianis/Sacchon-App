package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctor;
import com.pfizer.sacchon.team3.model.Patient;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatientListResourceImpl
        extends ServerResource implements PatientListResource{

    public static final Logger LOGGER = Engine.getLogger(PatientListResourceImpl.class);

    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            System.out.println("Doc Init");
            patientRepository = new PatientRepository(JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {

        }

        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public List<PatientRepresentation> getPatients() throws NotFoundException {
        LOGGER.finer("Select all patients.");
        System.out.println("Before role ckeck");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        System.out.println("After role check");
        try{

            List<Patient> patients = patientRepository.findAllPatients();
            for (Patient p: patients) {
                Hibernate.initialize(p.getPatientRecords());
            }
            List<PatientRepresentation> result = new ArrayList<>();

            patients.forEach(pat -> result.add (new PatientRepresentation(pat)));
            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("doctors not found");
        }
    }
}
