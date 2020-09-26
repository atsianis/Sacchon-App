package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.ConsultationRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
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

    private long id;
    private ConsultationRepository consultationRepository ;
    private ConsultationRepresentation consultationRepresentation;
    private EntityManager em;

    @Override
    protected void doRelease(){
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            consultationRepository = new ConsultationRepository(JpaUtil.getEntityManager());
            consultationRepresentation = new ConsultationRepresentation();
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            id = -1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    public ConsultationRepresentation UpdateConsultation(ConsultationRepresentation consultReprIn, Doctors doctor) throws NotFoundException, BadEntityException {

        LOGGER.finer("Update a consultation.");

        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        LOGGER.finer("User allowed to update a consultation.");
        // Check given entity
        // Convert to PatientRepr so a validation can procceed
        DoctorRepresentation dr = new DoctorRepresentation();
        dr.setLastName(doctor.getLastName());
        dr.setFirstName(doctor.getFirstName());

        ResourceValidator.notNull(consultReprIn);
        ResourceValidator.validateDoctor(dr);
        LOGGER.finer("Doctor checked");

        try {
            // Convert PatientRecordRepr to PatientRecord
            Consultations consultationsIn = consultReprIn.createConsultation();
            consultationsIn.setId(id);
            Optional<Consultations> consultationOut = consultationRepository.findById(id);
            setExisting(consultationOut.isPresent());
            // If patientRecord exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update consultation.");
                // Update Record in DB and retrieve the new one.


                consultationOut = consultationRepository.setComment(consultationsIn);

                // Create new Consultation with initialized patient_id and Date
                Consultations consultation = consultationRepresentation.createConsultation();
                consultation.setPatient(consultReprIn.getPatient());
                consultation.setTimeCreated(new Date());
                // Add consult in db
                Optional<Consultations> consultationsOut = consultationRepository.save(consultation);


                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!consultationOut.isPresent()) {
                    LOGGER.finer("Consultation does not exist.");
                    throw new NotFoundException("Consultation with the following id does not exist: " + id);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                throw new NotFoundException(
                        "Company with the following id does not exist: " + id);
            }

            LOGGER.finer("Company successfully updated.");
            return new ConsultationRepresentation(consultationOut.get());

        } catch (Exception ex) {

            throw new ResourceException(ex);
        }
    }
}
