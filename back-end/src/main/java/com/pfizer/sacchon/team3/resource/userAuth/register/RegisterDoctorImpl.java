package com.pfizer.sacchon.team3.resource.userAuth.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedDoctorRepresentation;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.jetbrains.annotations.NotNull;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterDoctorImpl extends ServerResource implements RegisterDoctor {
    public static final Logger LOGGER = Engine.getLogger(RegisterDoctorImpl.class);
    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    @Override
    public ResponseRepresentation<DoctorRepresentation> registerDoctor(CreatedOrUpdatedDoctorRepresentation createdOrUpdatedDoctorRepresentation) {
        LOGGER.finer("Add a new doctor.");
        try {
            ResourceValidator.notNull(createdOrUpdatedDoctorRepresentation);
            ResourceValidator.validate(createdOrUpdatedDoctorRepresentation);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }
        LOGGER.finer("doctor checked");
        try {
            Doctors doctorsIn = getToBePersistedDoctor(createdOrUpdatedDoctorRepresentation);

            Optional<Doctors> doctorOut = doctorRepository.save(doctorsIn);
            Doctors doctor;
            if (doctorOut.isPresent())
                doctor = doctorOut.get();
            else
                return new ResponseRepresentation<>(404, "Doctor not found", null);

            DoctorRepresentation result = getDoctorRepresentationOut(doctor);

            LOGGER.finer("doctor successfully added.");

            return new ResponseRepresentation<>(200, "Doctor registered successfully", result);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Error when adding a doctor", ex);

            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }
    }

    /**
     * @param doctors
     * @return DoctorRepresentation
     * <p>
     * converts the persisted Doctor to a DoctorRepresentation type object
     * that will be returned to the client
     */
    @NotNull
    private DoctorRepresentation getDoctorRepresentationOut(Doctors doctors) {
        DoctorRepresentation result = new DoctorRepresentation();
        result.setFirstName(doctors.getFirstName());
        result.setLastName(doctors.getLastName());
        result.setEmail(doctors.getEmail());
        result.setPassword(doctors.getPassword());
        result.setDeleted(doctors.isDeleted());
        result.setId(doctors.getId());

        return result;
    }

    /**
     * @param createdOrUpdatedDoctorRepresentation
     * @return a Doctor entity
     * <p>
     * convert the DoctorRepresentation input into the Doctor entity
     * that will be attempted to be persisted into the Database
     */
    @NotNull
    private Doctors getToBePersistedDoctor(CreatedOrUpdatedDoctorRepresentation createdOrUpdatedDoctorRepresentation) {
        Doctors doctorsIn = new Doctors();
        doctorsIn.setFirstName(createdOrUpdatedDoctorRepresentation.getFirstName());
        doctorsIn.setLastName(createdOrUpdatedDoctorRepresentation.getLastName());
        doctorsIn.setEmail(createdOrUpdatedDoctorRepresentation.getEmail());
        doctorsIn.setPassword(createdOrUpdatedDoctorRepresentation.getPassword());
        doctorsIn.setDeleted(false);

        return doctorsIn;
    }
}
