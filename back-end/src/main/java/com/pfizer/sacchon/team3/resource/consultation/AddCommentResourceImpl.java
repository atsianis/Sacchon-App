package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedConsultRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

public class AddCommentResourceImpl extends ServerResource implements AddCommentResource {
    public static final Logger LOGGER = Engine.getLogger(AddCommentResourceImpl.class);
    private long doctor_id;
    private long patient_id;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private ConsultationRepository consultationRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
            doctor_id = Long.parseLong(getAttribute("did"));
            patient_id = Long.parseLong(getAttribute("pid"));
        } catch (Exception e) {
            patient_id = -1;
            doctor_id = -1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    public ResponseRepresentation<ConsultationRepresentation> addCommentConsultation(CreatedOrUpdatedConsultRepresentation consultReprIn) {
        LOGGER.finer("Create a consultation.");
        if (consultReprIn.getComment().isEmpty())
            return new ResponseRepresentation<ConsultationRepresentation>(422, "Bad Entity", null);
        // get Doctor
        Optional<Doctors> odoctor = doctorRepository.findById(doctor_id);
        Doctors doctor;
        setExisting(odoctor.isPresent());
        if (!isExisting()) {
            LOGGER.config("Doctor id does not exist:" + doctor_id);
            return new ResponseRepresentation<ConsultationRepresentation>(404, "Doctor not found", null);
        } else {
            doctor = odoctor.get();
            LOGGER.finer("Doctor found");
        }
        // We have the doctor
        // Now we find the patient
        Optional<Patients> opatient = patientRepository.findById(patient_id);
        Patients patient;
        setExisting(opatient.isPresent());
        if (!isExisting()) {
            LOGGER.config("Patient id does not exist:" + patient_id);
            return new ResponseRepresentation<ConsultationRepresentation>(404, "Patient not found", null);
        } else {
            patient = opatient.get();
            LOGGER.finer("Patient found");
        }
        //Check if this patient belongs to this doctor
        if (!isMyPatient(doctor,patient))
            return new ResponseRepresentation<ConsultationRepresentation>(401, "Unauthorized ! Cant consult this patient", null);
        try {
            Consultations consultation = new Consultations();
            consultation.setDoctor(doctor);
            consultation.setComment(consultReprIn.getComment());
            consultation.setPatient(patient);
            consultation.setTimeCreated(new Date());
            Optional<Consultations> oconsultation = consultationRepository.save(consultation);
            setExisting(oconsultation.isPresent());
            if (isExisting())
                return new ResponseRepresentation<>(200,"Consultation Created",new ConsultationRepresentation(oconsultation.get()));
            return new ResponseRepresentation<ConsultationRepresentation>(422, "Consultation could not be created", null);
        } catch (Exception ex) {
            return new ResponseRepresentation<ConsultationRepresentation>(422, "Consultation could not be created", null);
        }
    }

    private boolean isMyPatient(Doctors d, Patients p) {
        return p.getDoctor().getId() == d.getId();
    }
}
