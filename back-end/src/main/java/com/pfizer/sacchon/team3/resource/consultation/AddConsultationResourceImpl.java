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

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

public class AddConsultationResourceImpl extends ServerResource implements AddConsultationResource {
    public static final Logger LOGGER = Engine.getLogger(AddConsultationResourceImpl.class);
    private long doctor_id;
    private long patient_id;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;
    private ConsultationRepository consultationRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease(){
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            patientRepository = new PatientRepository(em);
            doctorRepository = new DoctorRepository(em);
            consultationRepository = new ConsultationRepository(em);
            doctor_id = Long.parseLong(getAttribute("doctor_id"));
            patient_id = Long.parseLong(getAttribute("patient_id"));
        } catch (Exception e) {
            patient_id = -1;
            doctor_id = -1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    public ResponseRepresentation<ConsultationRepresentation> addConsultation(CreatedOrUpdatedConsultRepresentation consultReprIn) {
        LOGGER.finer("Create a consultation.");
        if (consultReprIn.getComment().isEmpty())
            return new ResponseRepresentation<>(422, "Bad Entity", null);

        Optional<Doctors> optionalDoctor = doctorRepository.findById(doctor_id);
        Doctors doctor;
        setExisting(optionalDoctor.isPresent());
        if (!isExisting()) {
            LOGGER.config("Doctor id does not exist:" + doctor_id);
            return new ResponseRepresentation<>(404, "Doctor not found", null);
        } else {
            doctor = optionalDoctor.get();
            LOGGER.finer("Doctor found");
        }
        Optional<Patients> optionalPatient = patientRepository.findById(patient_id);
        Patients patient;
        setExisting(optionalPatient.isPresent());
        if (!isExisting()) {
            LOGGER.config("Patient id does not exist:" + patient_id);
            return new ResponseRepresentation<>(404, "Patient not found", null);
        } else {
            patient = optionalPatient.get();
            LOGGER.finer("Patient found");
        }
        if (!isMyPatient(doctor,patient))
            return new ResponseRepresentation<>(401, "Unauthorized ! Cant consult this patient", null);

        if ( patientRepository.lastConsultationInDays(patient.getConsultations()) < 30 ){
            return new ResponseRepresentation<>(401,"Unauthorized ! An active Consultation already exists",null);
        }
        try {
            Optional<Consultations> optionalConsultaion = getConsultationPersistAttempt(consultReprIn, doctor, patient);
            setExisting(optionalConsultaion.isPresent());
            if (isExisting()){
                patientRepository.updateCanBeExamined(patient);
                return new ResponseRepresentation<>(200,"Consultation Created",new ConsultationRepresentation(optionalConsultaion.get()));
            }
            return new ResponseRepresentation<>(422, "Consultation could not be created", null);
        } catch (Exception ex) {
            return new ResponseRepresentation<>(422, "Consultation could not be created", null);
        }
    }

    /**
     *
     * @param consultReprIn
     * @param doctor
     * @param patient
     * @return Optional of type Consultation or null
     *
     * Convert the input Representation into a Consultation Entity
     * and attempt to persist in the database
     */
    private Optional<Consultations> getConsultationPersistAttempt(CreatedOrUpdatedConsultRepresentation consultReprIn, Doctors doctor, Patients patient) {
        Consultations consultation = new Consultations();
        consultation.setDoctor(doctor);
        consultation.setComment(consultReprIn.getComment());
        consultation.setPatient(patient);
        consultation.setTimeCreated(new Date());
        Optional<Consultations> oconsultation = consultationRepository.save(consultation);

        return oconsultation;
    }

    /**
     *
     * @param doctor
     * @param patient
     * @return boolean
     *
     * check if the attempted consultation represents a valid patient-doctor relationship
     */
    private boolean isMyPatient(Doctors doctor, Patients patient) {
        return patient.getDoctor().getId() == doctor.getId();
    }
}
