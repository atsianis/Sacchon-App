package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class DoctorConsultationsImpl extends ServerResource implements DoctorConsultations {

    public static final Logger LOGGER = Engine.getLogger(DoctorConsultationsImpl.class);
    private ConsultationRepository consultationRepository;
    private DoctorRepository doctorRepository;
    private long id;

    @Override
    protected void doInit() {
        LOGGER.finer("Doctor's Consultations Resource starts");
        try {
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.finer("Doctor's Consultations Resource ended");
    }

    @Override
    public ResponseRepresentation<List<ConsultationRepresentation>> getConsultationsByDoctor() {
        LOGGER.info("Retrieve doctor's consultations");
        try {
            Optional<Doctors> oDoctor = doctorRepository.findById(id);
            setExisting(oDoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("doctor id does not exist:" + id);
                return new ResponseRepresentation<>(404, "Doctor not found", null);
            } else {
                List<Consultations> patientsConsultations = consultationRepository.findDoctorConsultations(id);
                List<ConsultationRepresentation> result = new ArrayList<>();
                for (Consultations c : patientsConsultations)
                    result.add(new ConsultationRepresentation(c));

                return new ResponseRepresentation<>(200, "Consults retrieved", result);
            }

        } catch (Exception ex) {
            return new ResponseRepresentation<>(404, "Consults not found", null);
        }
    }
}

