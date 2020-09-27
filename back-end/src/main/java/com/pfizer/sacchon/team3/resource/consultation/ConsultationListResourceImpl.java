package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.doctor.DoctorResourceImpl;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConsultationListResourceImpl extends ServerResource implements ConsultationListResource {
    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private ConsultationRepository consultationRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultations resource starts");
        try {
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising consultations resource ends");
    }

    @Override
    public ResponseRepresentation<List<ConsultationRepresentation>> getConsultations(){
        LOGGER.finer("Select all consultations.");
        try {
            List<Consultations> consultations = consultationRepository.findAll();
            List<ConsultationRepresentation> result = new ArrayList<>();
            consultations.forEach(cons -> result.add(new ConsultationRepresentation(cons)));

            return new ResponseRepresentation<List<ConsultationRepresentation>>(200,"Consultations retrieved",result);
        } catch (Exception e) {
            return new ResponseRepresentation<List<ConsultationRepresentation>>(404,"Consultations not found",null);

        }
    }
}
