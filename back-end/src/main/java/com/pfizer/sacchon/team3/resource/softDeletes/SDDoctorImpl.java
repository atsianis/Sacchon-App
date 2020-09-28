package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.util.ResourceValidator;
import org.restlet.engine.Engine;
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
    public ResponseRepresentation<DoctorRepresentation> softDelete(DoctorRepresentation doctorRepresentation){
        LOGGER.finer("Soft Delete a doctor.");
        // Check given entity
        try{
            ResourceValidator.notNull(doctorRepresentation);
            ResourceValidator.validateDoctor(doctorRepresentation);
        }catch(BadEntityException ex){
            return new ResponseRepresentation<DoctorRepresentation>(422,"Bad Entity Exception",null);
        }
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

                    return new ResponseRepresentation<DoctorRepresentation>(404,"Doctor not found",null);
                }
            } else {
                LOGGER.finer("Doctor does not exist.");

                return new ResponseRepresentation<DoctorRepresentation>(404,"Doctor not found",null);
            }
            LOGGER.finer("Doctor successfully updated.");

            return new ResponseRepresentation<DoctorRepresentation>(200,"Doctor has been softly deleted",new DoctorRepresentation(doctorsOut.get()));
        } catch (Exception ex) {
            return new ResponseRepresentation<DoctorRepresentation>(404,"Doctor not found",null);
        }
    }
}
