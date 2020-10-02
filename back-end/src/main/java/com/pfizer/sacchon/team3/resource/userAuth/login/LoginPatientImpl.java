package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class LoginPatientImpl extends ServerResource implements LoginPatient {
    public static final Logger LOGGER = Engine.getLogger(LoginPatientImpl.class);
    private PatientRepository patientRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public ResponseRepresentation<PatientRepresentation> loginPatient(LoginRepresentation loginRepresentation) {
        LOGGER.info("Login Patient");
        // Initialize the persistence layer
        Patients patient;
        try {
            Optional<Patients> opPatient = patientRepository.findByEmailAndPass(loginRepresentation.getEmail(), loginRepresentation.getPassword());
            setExisting(opPatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("email does not exist:" + loginRepresentation.getEmail());
                return new ResponseRepresentation<>(404, "Patient not found", null);
            } else {
                patient = opPatient.get();
                PatientRepresentation result = new PatientRepresentation(patient);
                LOGGER.finer("Patient successfully logged in");

                return new ResponseRepresentation<>(200, "Logged in", result);
            }
        } catch (Exception ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }
    }
}
