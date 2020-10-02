package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class SoftDeletePatientImpl extends ServerResource implements SoftDeletePatient {
    public static final Logger LOGGER = Engine.getLogger(SoftDeletePatientImpl.class);
    private long id;
    private PatientRepository patientRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(em);
            id = Long.parseLong(getAttribute("patient_id"));
        } catch (Exception e) {
            id = -1;
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public ResponseRepresentation<PatientRepresentation> softDelete() {
        LOGGER.finer("Soft Delete a patient.");
        try {
            Optional<Patients> patientOut = patientRepository.findById(id);
            setExisting(patientOut.isPresent());
            // If patient exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Soft delete Patient.");
                Optional patientDeleted = patientRepository.softDelete(patientOut.get());
                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!patientDeleted.isPresent()) {
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
