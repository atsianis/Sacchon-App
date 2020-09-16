package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctor;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.print.Doc;
import java.util.Optional;
import java.util.logging.Logger;

public class DoctorResourceImpl  extends ServerResource implements DoctorResource{

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);

    private long id;

    @Override
    protected void doInit() {
       LOGGER.info("Initialising doctor resource starts");
        id = Long.parseLong(getAttribute("id"));
        LOGGER.info("Initialising doctor resource ends");
    }

    @Override
    public DoctorRepresentation getDoctor() throws NotFoundException {
        LOGGER.info("Retrieve a product");

        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);


        // Initialize the persistence layer.
        DoctorRepository doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        Doctor doctor;
        try {


            Optional<Doctor> odoctor = doctorRepository.findById(id);


            setExisting(odoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("doctor id does not exist:" + id);
                throw new NotFoundException("No product with  : " + id);
            } else {
                doctor = odoctor.get();
                LOGGER.finer("User allowed to retrieve a doctor.");
                //System.out.println(  userId);
                DoctorRepresentation result =
                        new DoctorRepresentation(doctor);


                LOGGER.finer("Doctor successfully retrieved");

                return result;

            }


        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    @Override
    public void remove() throws NotFoundException {

    }

    @Override
    public DoctorRepresentation store(DoctorRepresentation doctorReprIn) throws NotFoundException, BadEntityException {
        return null;
    }

    @Override
    public DoctorRepresentation add(DoctorRepresentation companyReprIn) throws BadEntityException {
        return null;
    }
}
