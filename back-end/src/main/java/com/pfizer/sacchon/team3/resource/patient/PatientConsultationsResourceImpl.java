package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PatientConsultationsResourceImpl extends ServerResource implements PatientConsultationsResource {

    public static final Logger LOGGER = Engine.getLogger(PatientConsultationsResourceImpl.class);
    private ConsultationRepository consultationRepository;
    private PatientRepository patientRepository;
    private long patient_id;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease(){
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Patients Consultations Resource starts");
        try {
            consultationRepository = new ConsultationRepository(em);
            patientRepository = new PatientRepository(em);
            patient_id = Long.parseLong(getAttribute("patient_id"));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.info("Patients Consultations Resource ended");
    }

    @Override
    public ResponseRepresentation<List<ConsultationRepresentation>> getPatientsConsultations() {
        LOGGER.info("Retrieve patient's consultations");
        try {
            Optional<Patients> opPatient = patientRepository.findById(patient_id);
            setExisting(opPatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + patient_id);
                return new ResponseRepresentation<>(404, "Consults not found", null);
            } else {
                List<Consultations> patientsConsultations = consultationRepository.findPatientsConsultations(patient_id);
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
