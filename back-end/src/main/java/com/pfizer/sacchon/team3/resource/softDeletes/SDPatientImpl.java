package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.patient.PatientResourceImpl;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class SDPatientImpl extends ServerResource implements SoftDeletePatient {
    public static final Logger LOGGER = Engine.getLogger(SDPatientImpl.class);
    private long id;
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            id = -1;
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public PatientRepresentation softDelete(PatientRepresentation patientRepresentation) throws NotFoundException, BadEntityException {
        LOGGER.finer("Soft Delete a patient.");
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);
        LOGGER.finer("User allowed to soft Delete a patient.");
        // Check given entity
        ResourceValidator.notNull(patientRepresentation);
        ResourceValidator.validatePatient(patientRepresentation);
        LOGGER.finer("Patient checked");

        try {
            // Convert PatientRepr to Patient
            Patients patientsIn = patientRepresentation.createPatient();
            patientsIn.setId(id);
            Optional<Patients> patientOut = patientRepository.findById(id);
            setExisting(patientOut.isPresent());
            // If patient exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Soft delete Patient.");
                patientOut = patientRepository.softDelete(patientsIn);
                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!patientOut.isPresent()) {
                    LOGGER.finer("Patient does not exist.");
                    throw new NotFoundException("Patient with the following id does not exist: " + id);
                }
            } else {
                LOGGER.finer("Patient does not exist.");
                throw new NotFoundException("Patient with the following id does not exist: " + id);
            }
            LOGGER.finer("Patient successfully updated.");

            return new PatientRepresentation(patientOut.get());
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
