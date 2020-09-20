package com.pfizer.sacchon.team3.resource.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.doctor.DoctorResourceImpl;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterDoctorImpl extends ServerResource implements RegisterDoctor {
    public static final Logger LOGGER = Engine.getLogger(RegisterDoctorImpl.class);
    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    @Override
    public DoctorRepresentation add(DoctorRepresentation doctorRepresentation) throws BadEntityException {
        LOGGER.finer("Add a new doctor.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        LOGGER.finer("User allowed to add a doctor.");
        // Check entity
        ResourceValidator.notNull(doctorRepresentation);
        ResourceValidator.validateDoctor(doctorRepresentation);
        LOGGER.finer("doctor checked");

        try {
            // Convert DoctorRepr to doctor
            Doctors doctorsIn = new Doctors();
            doctorsIn.setFirstName(doctorRepresentation.getFirstName());
            doctorsIn.setLastName(doctorRepresentation.getLastName());
            doctorsIn.setEmail(doctorRepresentation.getEmail());
            doctorsIn.setPassword(doctorRepresentation.getPassword());

            Optional<Doctors> doctorOut = doctorRepository.save(doctorsIn);
            Doctors doctors = null;
            if (doctorOut.isPresent())
                doctors = doctorOut.get();
            else
                throw new BadEntityException(" doctor has not been created");

            DoctorRepresentation result = new DoctorRepresentation();
            result.setFirstName(doctors.getFirstName());
            result.setLastName(doctors.getLastName());
            result.setEmail(doctors.getEmail());
            result.setPassword(doctors.getPassword());
            result.setUri("http://localhost:9000/v1/doctor/" + doctors.getId());

            getResponse().setLocationRef("http://localhost:9000/v1/doctor/" + doctors.getId());
            getResponse().setStatus(Status.SUCCESS_CREATED);

            LOGGER.finer("doctor successfully added.");

            return result;
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a doctor", ex);
            throw new ResourceException(ex);
        }
    }
}
