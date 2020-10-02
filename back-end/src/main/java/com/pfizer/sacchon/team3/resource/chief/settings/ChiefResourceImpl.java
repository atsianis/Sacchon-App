package com.pfizer.sacchon.team3.resource.chief.settings;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Chiefs;
import com.pfizer.sacchon.team3.repository.ChiefRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ChiefRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.jetbrains.annotations.NotNull;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class ChiefResourceImpl extends ServerResource implements ChiefResource {
    public static final Logger LOGGER = Engine.getLogger(ChiefResourceImpl.class);
    private long chief_id;
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
            chief_id = Long.parseLong(getAttribute("chief_id"));
        } catch (Exception e) {
            chief_id = -1;
        }
        LOGGER.info("Initialising chief resource ends");
    }

    @Override
    public ResponseRepresentation<ChiefRepresentation> update(ChiefRepresentation chiefRepresentation) {
        LOGGER.finer("Update chief.");
        // Check given entity
        try {
            ResourceValidator.notNull(chiefRepresentation);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "BadEntity", null);
        }

        LOGGER.finer("Chief checked");

        try {
            Optional<Chiefs> chiefOut = chiefRepository.findById(chief_id);
            setExisting(chiefOut.isPresent());
            Chiefs chiefToBePersisted;
            // If patient exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update patient.");
                chiefToBePersisted = getChiefToBePersisted(chiefRepresentation, chiefOut);
                // Update patient in DB and retrieve the new one.
                chiefOut = chiefRepository.update(chiefToBePersisted);
                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!chiefOut.isPresent()) {
                    LOGGER.finer("Patient does not exist.");
                    return new ResponseRepresentation<>(404, "SQL Exception", null);
                }
            } else {
                LOGGER.finer("Patient does not exist.");
                return new ResponseRepresentation<>(404, "Chief not found", null);
            }
            LOGGER.finer("Patient successfully updated.");
            return new ResponseRepresentation<>(200, "Chief created", new ChiefRepresentation(chiefOut.get()));
        } catch (Exception ex) {
            return new ResponseRepresentation<>(404, "Chief not found 2", null);
        }
    }

        @NotNull
        private Chiefs getChiefToBePersisted(ChiefRepresentation chiefRepresentation, Optional<Chiefs> chiefOut) {
            Chiefs chief = chiefOut.get();
            if (!(chiefRepresentation.getPassword()==null))
                chief.setPassword((chiefRepresentation.getPassword()));
            if (!(chiefRepresentation.getFirstName()==null))
                chief.setFirstName(chiefRepresentation.getFirstName());
            if (!(chiefRepresentation.getLastName()==null))
                chief.setLastName(chiefRepresentation.getLastName());
            if (!(chiefRepresentation.getEmail()==null))
                chief.setEmail(chiefRepresentation.getEmail());

            return chief;
        }
}
