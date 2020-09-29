package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class SoftDeleteDoctorImpl extends ServerResource implements SoftDeleteDoctor {
    public static final Logger LOGGER = Engine.getLogger(SoftDeleteDoctorImpl.class);
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
    public ResponseRepresentation<DoctorRepresentation> softDelete() {
        LOGGER.finer("Soft Delete a doctor.");
        try {
            Optional<Doctors> doctorsOut = doctorRepository.findById(id);
            setExisting(doctorsOut.isPresent());

            if (isExisting()) {
                LOGGER.finer("Soft delete Doctor.");
                Optional doctorsDeleted = doctorRepository.softDelete(doctorsOut.get());
                if (!doctorsDeleted.isPresent()) {
                    LOGGER.finer("Doctor id not delete");

                    return new ResponseRepresentation<>(404, "Doctor did not delete", null);
                }
            } else {
                LOGGER.finer("Doctor does not exist.");

                return new ResponseRepresentation<>(404, "Doctor not found 1", null);
            }
            LOGGER.finer("Doctor successfully updated.");

            return new ResponseRepresentation<>(200, "Doctor has been softly deleted", new DoctorRepresentation(doctorsOut.get()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseRepresentation<>(404, "Doctor not found 2", null);
        }
    }
}
