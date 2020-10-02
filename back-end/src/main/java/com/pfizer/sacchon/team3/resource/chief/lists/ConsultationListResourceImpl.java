package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConsultationListResourceImpl extends ServerResource implements ConsultationListResource {
    public static final Logger LOGGER = Engine.getLogger(ConsultationListResourceImpl.class);
    private ConsultationRepository consultationRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultations resource starts");
        try {
            consultationRepository = new ConsultationRepository(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising consultations resource ends");
    }

    @Override
    public ResponseRepresentation<List<ConsultationRepresentation>> getConsultations() {
        LOGGER.finer("Select all consultations.");
        try {
            List<Consultations> consultations = consultationRepository.findAll();
            List<ConsultationRepresentation> result = new ArrayList<>();
            consultations.forEach(cons -> result.add(new ConsultationRepresentation(cons)));

            return new ResponseRepresentation<>(200, "Consultations retrieved", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Consultations not found", null);
        }
    }
}
