package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class LoginDoctorImpl extends ServerResource implements LoginDoctor {
    public static final Logger LOGGER = Engine.getLogger(LoginDoctorImpl.class);
    private DoctorRepository doctorRepository;
    private String email;
    private String password;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doc resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            email = getAttribute("email");
            password = getAttribute("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising doc resource ends");
    }

    @Override
    public DoctorRepresentation loginDoctor() throws NotFoundException {
        LOGGER.info("Login doctor");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        // Initialize the persistence layer
        Doctors doctor;
        try {
            Optional<Doctors> opDoctor = doctorRepository.findByEmailAndPass(email, password);
            setExisting(opDoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("email does not exist:" + email);
                throw new NotFoundException("No doctor with that email : " + email);
            } else {
                doctor = opDoctor.get();
                DoctorRepresentation result = new DoctorRepresentation(doctor);
                LOGGER.finer("Doctor successfully logged in");

                return result;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}