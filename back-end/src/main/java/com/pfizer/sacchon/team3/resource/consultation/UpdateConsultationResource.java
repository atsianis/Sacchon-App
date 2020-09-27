package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.BadInsertionException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.UpdateConsultationRepresentation;
import com.pfizer.sacchon.team3.resource.doctor.DoctorResourceImpl;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

public class UpdateConsultationResource extends ServerResource implements UpdateConsultation {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);

    private long doctor_id;
    private long consultation_id;
    private ConsultationRepository consultationRepository ;
    private DoctorRepository doctorRepository ;
    private ConsultationRepresentation consultationRepresentation;
    private EntityManager em;

//    @Override
//    protected void doRelease(){
//        em.close();
//    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
            consultationRepresentation = new ConsultationRepresentation();
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            doctor_id = Long.parseLong(getAttribute("did"));
            consultation_id = Long.parseLong(getAttribute("cid"));
        } catch (Exception e) {
            consultation_id = -1;
            doctor_id = -1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    public ConsultationRepresentation updateConsultation(UpdateConsultationRepresentation consultReprIn) throws NotFoundException, BadEntityException, BadInsertionException {

        LOGGER.finer("Update a consultation.");
        if (consultReprIn.getComment().isEmpty())
            throw new BadInsertionException("Cant post an empty consultation, doc !");
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        LOGGER.finer("User allowed to update a consultation.");
        // get Doctor
        Optional<Doctors> odoctor = doctorRepository.findById(doctor_id);
        Doctors doctor;
        setExisting(odoctor.isPresent());
        if (!isExisting()) {
            LOGGER.config("Doctor id does not exist:" + doctor_id);
            throw new NotFoundException("No doctor with id  : " + doctor_id);
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
                    throw new NotFoundException("This consult is not yours");
                }
                consultation.setComment(consultReprIn.getComment());
                LOGGER.finer("Update consultation.");
                // Update Record in DB and retrieve the new one.

                Optional<Consultations> oconsult = consultationRepository.setComment(consultation);
                setExisting(oconsult.isPresent());
                if(isExisting()){
                    doctor.setLastActive(new Date());
                    doctorRepository.update(doctor);
                    return new ConsultationRepresentation(oconsult.get());
                }else{
                    throw new NotFoundException("consultation is not found");
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException("Consultation with the following id does not exist: " + consultation_id);
            }
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    private boolean isMyConsult(Doctors d, Consultations c){
        return d.getId()==c.getDoctor().getId();
    }
}
