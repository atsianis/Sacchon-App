package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.restlet.engine.Engine;
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
    public ResponseRepresentation<PatientRepresentation> softDelete(PatientRepresentation patientRepresentation) {
        LOGGER.finer("Soft Delete a patient.");
        // Check given entity
        try {
            ResourceValidator.notNull(patientRepresentation);
            ResourceValidator.validatePatient(patientRepresentation);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "Bad Entity Exception", null);
        }

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
                    return new ResponseRepresentation<>(404, "Patient not found", null);
                }
            } else {
                LOGGER.finer("Patient does not exist.");
                return new ResponseRepresentation<>(404, "Patient not found", null);
            }
            LOGGER.finer("Patient successfully updated.");

            return new ResponseRepresentation<>(404, "Patient has been softly deleted", new PatientRepresentation(patientOut.get()));
        } catch (Exception ex) {
            return new ResponseRepresentation<>(404, "Patient not found", null);
        }
    }
}
