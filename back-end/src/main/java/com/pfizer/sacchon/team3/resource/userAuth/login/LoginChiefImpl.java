package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Chiefs;
import com.pfizer.sacchon.team3.repository.ChiefRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ChiefRepresentation;
import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Role;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class LoginChiefImpl extends ServerResource implements LoginChief {
    public static final Logger LOGGER = Engine.getLogger(LoginChiefImpl.class);
    private ChiefRepository chiefRepository;
    //private String email;
    private String password;
    //private LoginRepresentation loginRepresentation;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising chief resource starts");
        try {
            chiefRepository = new ChiefRepository(JpaUtil.getEntityManager());
            //email = getAttribute("email");
            password = getAttribute("password");
            //loginRepresentation.setEmail(getAttribute("email"));
            //loginRepresentation.setPassword(getAttribute("password"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising chief resource ends");
    }

    @Override
    public ChiefRepresentation loginChief(String email) throws NotFoundException {
        LOGGER.info("Login chief");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        // Initialize the persistence layer
        Chiefs chief;
        try {
            Optional<Chiefs> opChief = chiefRepository.findByEmail(email);
            setExisting(opChief.isPresent());
            if (!isExisting()) {
                LOGGER.config("email does not exist:" + email);
                throw new NotFoundException("No chief with that email : " + email);
            } else {
                chief = opChief.get();
                ChiefRepresentation result = new ChiefRepresentation(chief);
                LOGGER.finer("chief successfully logged in");

                return result;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
