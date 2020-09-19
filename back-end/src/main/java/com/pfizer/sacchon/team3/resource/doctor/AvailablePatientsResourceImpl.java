package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AvailablePatientsResourceImpl extends ServerResource implements AvailablePatients{

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private DoctorRepository doctorRepository ;

    @Override
    protected void doInit() {
        LOGGER.info("Available patients resource starts");
        try {
            doctorRepository = new DoctorRepository (JpaUtil.getEntityManager()) ;
        }
        catch(Exception e)
        {}
        LOGGER.info("Available patients resource ends");
    }

    @Override
    public List<PatientRepresentation> availablePatients() throws NotFoundException {
        LOGGER.finer("Select all available patients.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);

        try{

            List<Patients> patients = doctorRepository.availablePatients();
            List<PatientRepresentation> result = new ArrayList<>();
            patients.forEach(patient -> result.add(new PatientRepresentation(patient)));
            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("available patients not found");
        }
    }
}
