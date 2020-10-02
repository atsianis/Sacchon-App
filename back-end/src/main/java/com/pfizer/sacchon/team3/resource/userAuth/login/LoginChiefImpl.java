package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.model.Chiefs;
import com.pfizer.sacchon.team3.repository.ChiefRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ChiefRepresentation;
import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class LoginChiefImpl extends ServerResource implements LoginChief {
    public static final Logger LOGGER = Engine.getLogger(LoginChiefImpl.class);
    private ChiefRepository chiefRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease(){
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising chief resource starts");
        try {
            chiefRepository = new ChiefRepository(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising chief resource ends");
    }

    @Override
    public ResponseRepresentation<ChiefRepresentation> loginChief(LoginRepresentation loginRepresentation) {
        LOGGER.info("Login chief");
        // Initialize the persistence layer
        Chiefs chief;
        try {
            Optional<Chiefs> opChief = chiefRepository.findByEmailAndPass(loginRepresentation.getEmail(), loginRepresentation.getPassword());
            setExisting(opChief.isPresent());
            if (!isExisting()) {
                LOGGER.config("email does not exist:" + loginRepresentation.getEmail());
                return new ResponseRepresentation<>(401, "chief not found", null);
            } else {
                chief = opChief.get();
                ChiefRepresentation result = new ChiefRepresentation(chief);
                LOGGER.finer("chief successfully logged in");

                return new ResponseRepresentation<>(200, "logged in", result);
            }
        } catch (Exception ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }
    }
}
