package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PatientConsultationsResourceImpl extends ServerResource implements PatientConsultationsResource {

    public static final Logger LOGGER = Engine.getLogger(PatientConsultationsResourceImpl.class);
    private ConsultationRepository consultationRepository;
    private PatientRepository patientRepository;
    private long id;

    @Override
    protected void doInit() {
        LOGGER.info("Patients Consultations Resource starts");
        try {
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.info("Patients Consultations Resource ended");
    }

    @Override
    public List<ConsultationRepresentation> getPatientsConsultations() throws NotFoundException {
        LOGGER.info("Retrieve patient's consultations");
        try {
            Optional<Patients> opPatient = patientRepository.findById(id);
            setExisting(opPatient.isPresent());
            if (!isExisting()) {
                LOGGER.config("patient id does not exist:" + id);
                throw new NotFoundException("No patient with  : " + id);
            } else {
                List<Consultations> patientsConsultations = consultationRepository.findPatientsConsultations(id);
                List<ConsultationRepresentation> result = new ArrayList<>();
                for (Consultations c : patientsConsultations)
                    result.add(new ConsultationRepresentation(c));

                return result;
            }

        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }


}
