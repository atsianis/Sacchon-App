package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class LoginPatientImpl extends ServerResource implements LoginPatient {
    public static final Logger LOGGER = Engine.getLogger(LoginPatientImpl.class);
    private PatientRepository patientRepository;
    private String email;
    private String password;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            email = getAttribute("email");
            password = getAttribute("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public PatientRepresentation loginPatient() throws NotFoundException {
        LOGGER.info("Login Patient");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        // Initialize the persistence layer
        Patients patient;
        try {
            Optional<Patients> opPatient = patientRepository.findByEmailAndPass(email, password);
            setExisting(opPatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("email does not exist:" + email);
                throw new NotFoundException("No patient with that email : " + email);
            } else {
                patient = opPatient.get();
                PatientRepresentation result = new PatientRepresentation(patient);
                LOGGER.finer("Patient successfully logged in");

                return result;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
