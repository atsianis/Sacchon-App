package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedConsultRepresentation;
import com.pfizer.sacchon.team3.resource.doctor.DoctorResourceImpl;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

public class UpdateConsultationResource extends ServerResource implements UpdateConsultation {
    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private long doctor_id;
    private long consultation_id;
    private ConsultationRepository consultationRepository ;
    private DoctorRepository doctorRepository ;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            doctor_id = Long.parseLong(getAttribute("doctor_id"));
            consultation_id = Long.parseLong(getAttribute("consultation_id"));
        } catch (Exception e) {
            consultation_id = -1;
            doctor_id = -1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    public ResponseRepresentation<ConsultationRepresentation> updateConsultation(CreatedOrUpdatedConsultRepresentation consultReprIn){
        LOGGER.finer("Update a consultation.");
        if (consultReprIn.getComment().isEmpty())
            return new ResponseRepresentation<ConsultationRepresentation>(422,"Bad Entity",null);
        // get Doctor
        Optional<Doctors> odoctor = doctorRepository.findById(doctor_id);
        Doctors doctor;
        setExisting(odoctor.isPresent());
        if (!isExisting()) {
            LOGGER.config("Doctor id does not exist:" + doctor_id);
            return new ResponseRepresentation<ConsultationRepresentation>(404,"Doctor not found",null);
        } else {
            doctor = odoctor.get();
            LOGGER.finer("Doctor allowed to update a consultation.");
        }
        // We have the doctor
        // Now we find the consultation
        try {
            Optional<Consultations> consultationOut = consultationRepository.findById(consultation_id);
            Consultations consultation;
            setExisting(consultationOut.isPresent());
            // If patientRecord exists, we update it.
            if (isExisting()) {
                consultation= consultationOut.get();
                if (!isMyConsult(doctor,consultation)){
                    return new ResponseRepresentation<ConsultationRepresentation>(401,"Unauthorized ! Can't update someone else's consultation",null);
                }
                consultation.setComment(consultReprIn.getComment());
                LOGGER.finer("Update consultation.");
                // Update Record in DB and retrieve the new one.

                Optional<Consultations> oconsult = consultationRepository.setComment(consultation);
                setExisting(oconsult.isPresent());
                if(isExisting()){
                    doctor.setLastActive(new Date());
                    doctorRepository.update(doctor);

                    return new ResponseRepresentation<ConsultationRepresentation>(200,"Consultation updated",new ConsultationRepresentation(oconsult.get()));
                }else{
                    return new ResponseRepresentation<ConsultationRepresentation>(404,"Consultation not found",null);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                return new ResponseRepresentation<ConsultationRepresentation>(404,"Consultation not found",null);
            }
        } catch (Exception ex) {
            return new ResponseRepresentation<ConsultationRepresentation>(404,"Consultation not found",null);
        }
    }

    private boolean isMyConsult(Doctors d, Consultations c){
        return d.getId()==c.getDoctor().getId();
    }
}
