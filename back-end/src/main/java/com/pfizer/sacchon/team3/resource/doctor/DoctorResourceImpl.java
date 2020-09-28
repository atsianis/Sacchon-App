package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedDoctorRepresentation;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorResourceImpl extends ServerResource implements DoctorResource {
    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private long id;
    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            id = -1;
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    // GET a doctor
    @Override
    public ResponseRepresentation<DoctorRepresentation> getDoctor() {
        LOGGER.info("Retrieve a doctor");
        // Initialize the persistence layer.
        doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        Doctors doctor;
        try {
            Optional<Doctors> opDoctor = doctorRepository.findById(id);
            setExisting(opDoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("doctor id does not exist:" + id);
                return new ResponseRepresentation<>(404, "Doctor not found", null);
            } else {
                doctor = opDoctor.get();
                Hibernate.initialize(doctor.getPatients());
                Hibernate.initialize(doctor.getConsultations());
                LOGGER.finer("User allowed to retrieve a doctor.");
                DoctorRepresentation result = new DoctorRepresentation(doctor);
                LOGGER.finer("Doctor successfully retrieved");

                return new ResponseRepresentation<>(200, "Doctor successfully retrieved", result);

            }
        } catch (Exception ex) {
            return new ResponseRepresentation<>(404, "Doctor not found", null);
        }
    }

    // DELETE Doctor
    @Override
    public ResponseRepresentation<DoctorRepresentation> remove() {
        LOGGER.finer("Removal of doctor");
        try {
            Boolean isDeleted = doctorRepository.remove(id);
            if (!isDeleted) {
                LOGGER.config("Doctor id does not exist");
                return new ResponseRepresentation<>(404, "Doctor not found", null);

            }
            LOGGER.finer("Doctor successfully removed.");
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when removing a doctor", ex);
            return new ResponseRepresentation<>(404, "Doctor not found", null);
        }

        return new ResponseRepresentation<>(200, "Doctor deleted updated", null);
    }

    // UPDATE Doctor
    @Override
    public ResponseRepresentation<DoctorRepresentation> updateDoctor(CreatedOrUpdatedDoctorRepresentation doctorReprIn) {
        LOGGER.finer("Update a doctor.");
        // Check given entity
        try {
            ResourceValidator.notNull(doctorReprIn);
            ResourceValidator.validateDoctor(doctorReprIn);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }

        LOGGER.finer("Doctor checked");
        try {
            // Convert DoctorRepresentation to Doctor
            Doctors doctorIn = doctorReprIn.createDoctor();
            doctorIn.setId(id);
            Optional<Doctors> doctorOut;
            Optional<Doctors> oDoctor = doctorRepository.findById(id);
            setExisting(oDoctor.isPresent());
            // If doctor exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update doctor.");
                // Update doctor in DB and retrieve the new one.
                doctorOut = doctorRepository.update(doctorIn);
                // Check if retrieved doctor is not null : if it is null it
                // means that the id is wrong.
                if (!doctorOut.isPresent()) {
                    LOGGER.finer("Doctor does not exist.");
                    return new ResponseRepresentation<>(404, "Doctor not found", null);
                }
            } else {
                LOGGER.finer("Resource does not exist.");
                return new ResponseRepresentation<>(404, "Doctor not found", null);
            }
            LOGGER.finer("Doctor successfully updated.");
            return new ResponseRepresentation<>(200, "Doctor successfully updated", new DoctorRepresentation(doctorOut.get()));
        } catch (Exception ex) {
            return new ResponseRepresentation<>(404, "Doctor not found", null);
        }
    }
}
