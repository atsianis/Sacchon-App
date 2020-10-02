package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class ConsultationSeenByPatientImpl extends ServerResource implements ConsultationSeenByPatient {

    public static final Logger LOGGER = Engine.getLogger(ConsultationSeenByPatientImpl.class);
    private ConsultationRepository consultationRepository;
    private PatientRepository patientRepository;
    private long patient_id;
    private long consultation_id;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising Seen Consultation resource starts");
        try {
            consultationRepository = new ConsultationRepository(em);
            patientRepository = new PatientRepository(em);
            patient_id = Long.parseLong(getAttribute("patient_id"));
            consultation_id = Long.parseLong((getAttribute("consultation_id")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising Seen Consultation record resource ends");
    }

    @Override
    public ResponseRepresentation<ConsultationRepresentation> setConsultationAsSeen() {

        Optional<Consultations> optionalConsultation = consultationRepository.findById(consultation_id);
        setExisting(optionalConsultation.isPresent());
        if (!isExisting()) {
            LOGGER.config("Consultation id does not exist:" + consultation_id);

            return new ResponseRepresentation<>(404, "Consultation not found", null);
        } else {
            LOGGER.finer("Consultation found");
        }

        Optional<Patients> optionalPatient = patientRepository.findById(patient_id);
        setExisting(optionalPatient.isPresent());
        if (!isExisting()) {
            LOGGER.config("Patient id does not exist:" + patient_id);

            return new ResponseRepresentation<>(404, "Patient not found", null);
        } else {
            LOGGER.finer("Patient found");
        }

        try {
            Optional consultationOut = consultationRepository.setSeen(consultation_id);
            setExisting(consultationOut.isPresent());
            ConsultationRepresentation result;
            if (isExisting()) {
                result = new ConsultationRepresentation((Consultations) consultationOut.get());
            } else {
                return new ResponseRepresentation<>(404, "Consultation not found", null);
            }

            return new ResponseRepresentation<>(200, "Consultation is now seen", result);
        } catch (Exception ex) {
            return new ResponseRepresentation<>(500, "Could not update consultation state", null);
        }
    }
}
