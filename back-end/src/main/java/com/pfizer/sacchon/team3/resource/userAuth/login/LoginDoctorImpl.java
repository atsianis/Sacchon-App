package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class LoginDoctorImpl extends ServerResource implements LoginDoctor {
    public static final Logger LOGGER = Engine.getLogger(LoginDoctorImpl.class);
    private DoctorRepository doctorRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doc resource starts");
        try {
            doctorRepository = new DoctorRepository(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising doc resource ends");
    }

    @Override
    public ResponseRepresentation<DoctorRepresentation> loginDoctor(LoginRepresentation loginRepresentation) {
        LOGGER.info("Login doctor");
        // Initialize the persistence layer
        Doctors doctor;
        try {
            Optional<Doctors> opDoctor = doctorRepository.findByEmailAndPass(loginRepresentation.getEmail(), loginRepresentation.getPassword());
            setExisting(opDoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("email does not exist:" + loginRepresentation.getEmail());
                return new ResponseRepresentation<>(401, "Doctor not found", null);
            } else {
                doctor = opDoctor.get();
                DoctorRepresentation result = new DoctorRepresentation(doctor);
                LOGGER.finer("Doctor successfully logged in");

                return new ResponseRepresentation<>(200, "Logged In", result);
            }
        } catch (Exception ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }
    }
}