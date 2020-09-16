package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctor;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DoctorListResourceImpl
        extends ServerResource implements DoctorListResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);

    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising product resource starts");
        try {
            doctorRepository =
                    new DoctorRepository(JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {

        }

        LOGGER.info("Initialising product resource ends");
    }

    @Override
    public List<DoctorRepresentation> getDoctors() throws NotFoundException{
        LOGGER.finer("Select all products.");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);

        try{

            List<Doctor> doctors =
                    doctorRepository.findAll();
            List<DoctorRepresentation> result = new ArrayList<>();

            doctors.forEach(doc ->
                    result.add (new DoctorRepresentation(doc)));


            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("products not found");
        }
    }


}
