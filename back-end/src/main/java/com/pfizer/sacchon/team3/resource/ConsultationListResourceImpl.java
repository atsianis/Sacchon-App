package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Consultation;
import com.pfizer.sacchon.team3.model.Doctor;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConsultationListResourceImpl
        extends ServerResource implements ConsultationListResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);

    private ConsultationRepository consultationRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultations resource starts");
        try {
            System.out.println("Cons Init");
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager()) ;

        }
        catch(Exception e)
        {

        }

        LOGGER.info("Initialising consultations resource ends");
    }

    @Override
    public List<ConsultationRepresentation> getConsultations() throws NotFoundException {
        LOGGER.finer("Select all consultations.");
        System.out.println("Before role check");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        System.out.println("After role check");
        try{

            List<Consultation> consultations = consultationRepository.findAll();
            for (Consultation cons: consultations) {
                Hibernate.initialize(cons.getPatientRecords());
            }
            List<ConsultationRepresentation> result = new ArrayList<>();

            consultations.forEach(cons -> result.add (new ConsultationRepresentation(cons)));
            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("consultations not found");
        }
    }

}
