package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class SDDoctorImpl extends ServerResource implements SoftDeleteDoctor {
    public static final Logger LOGGER = Engine.getLogger(SDDoctorImpl.class);
    private long id;
    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            id = -1;
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public DoctorRepresentation softDelete(DoctorRepresentation doctorRepresentation) throws NotFoundException, BadEntityException {
        LOGGER.finer("Soft Delete a doctor.");
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        LOGGER.finer("User allowed to soft Delete a doctor.");
        // Check given entity
        ResourceValidator.notNull(doctorRepresentation);
        ResourceValidator.validateDoctor(doctorRepresentation);
        LOGGER.finer("Doctor checked");

        try {
            // Convert DoctorRepr to Doctor
            Doctors doctorsIn = doctorRepresentation.createDoctor();
            doctorsIn.setId(id);
            Optional<Doctors> doctorsOut = doctorRepository.findById(id);
            setExisting(doctorsOut.isPresent());

            if (isExisting()) {
                LOGGER.finer("Soft delete Doctor.");
                doctorsOut = doctorRepository.softDelete(doctorsIn);
                if (!doctorsOut.isPresent()) {
                    LOGGER.finer("Doctor does not exist.");
                    throw new NotFoundException("Doctor with the following id does not exist: " + id);
                }
            } else {
                LOGGER.finer("Doctor does not exist.");
                throw new NotFoundException("Doctor with the following id does not exist: " + id);
            }
            LOGGER.finer("Doctor successfully updated.");

            return new DoctorRepresentation(doctorsOut.get());
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }
}
