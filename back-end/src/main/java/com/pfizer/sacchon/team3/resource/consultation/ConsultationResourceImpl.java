package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.resource.doctor.DoctorResourceImpl;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class ConsultationResourceImpl extends ServerResource implements ConsultationResource {
    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private long id;
    private ConsultationRepository consultationRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultation resource starts");
        try {
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            id = -1;
        }
        LOGGER.info("Initialising consultation resource ends");
    }

    @Override
    public ConsultationRepresentation getConsultation() throws NotFoundException {
        LOGGER.info("Retrieve a consultation");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        LOGGER.info("Passed Authorization");
        // Initialize the persistence layer.
        ConsultationRepository consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
        Consultations consultation;
        try {
            Optional<Consultations> opConsultation = consultationRepository.findById(id);
            setExisting(opConsultation.isPresent());
            if (!isExisting()) {
                LOGGER.config("Consultation id does not exist:" + id);
                throw new NotFoundException("No consultation with  : " + id);
            } else {
                consultation = opConsultation.get();
                LOGGER.finer("User allowed to retrieve a consultation.");
                ConsultationRepresentation result = new ConsultationRepresentation(consultation);
                LOGGER.finer("Consultation successfully retrieved");

                return result;
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
