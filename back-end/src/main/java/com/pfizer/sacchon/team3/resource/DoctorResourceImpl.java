package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.hibernate.Hibernate;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorResourceImpl
        extends ServerResource implements DoctorResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private long id;
    private DoctorRepository doctorRepository ;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager()) ;
            id = Long.parseLong(getAttribute("id"));
        }
        catch(Exception e) {
            id =-1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    @Override
    public DoctorRepresentation getDoctor() throws NotFoundException {
        LOGGER.info("Retrieve a doctor");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        LOGGER.info("Passed Authorization");
        // Initialize the persistence layer.
        DoctorRepository doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        Doctors doctor;
        try {
            Optional<Doctors> doctorFromDB = doctorRepository.findById(id);

            setExisting(doctorFromDB.isPresent());
            if (!isExisting()) {
                LOGGER.config("Doctor id does not exist:" + id);
                throw new NotFoundException("No doctor with  : " + id);
            } else {
                doctor = doctorFromDB.get();
                Hibernate.initialize(doctor.getConsultations());
                LOGGER.finer("User allowed to retrieve a doctor.");
                DoctorRepresentation result = new DoctorRepresentation(doctor);
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
    public DoctorRepresentation store(DoctorRepresentation doctorRepresentationIn) throws NotFoundException, BadEntityException {
        LOGGER.finer("Add a new doctor.");
        System.out.println("inside store");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        LOGGER.finer("User allowed to add a product.");

        // Check entity

        ResourceValidator.notNull(doctorRepresentationIn);
        ResourceValidator.validate(doctorRepresentationIn);
        LOGGER.finer("Product checked");
        System.out.println("inside store 2");
        try {

            // Convert DoctorRepresentation to Doctor
            Doctors doctorIn = new Doctors();
            doctorIn.setFirstName(doctorRepresentationIn.getFirstName());
            doctorIn.setLastName(doctorRepresentationIn.getLastName());
            doctorIn.setEmail(doctorRepresentationIn.getEmail());
            doctorIn.setPassword(doctorRepresentationIn.getPassword());


            Optional<Doctors> doctorOut = doctorRepository.save(doctorIn);
            Doctors doctor= null;
            if (doctorOut.isPresent())
                doctor = doctorOut.get();
            else
                throw new BadEntityException(" Doctor has not been created");

            DoctorRepresentation result = new DoctorRepresentation(doctor);

            getResponse().setLocationRef(
                    "http://localhost:9000/v1/product/"+doctor.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("Doctor successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a doctor", ex);

            throw new ResourceException(ex);
        }
    }


}
