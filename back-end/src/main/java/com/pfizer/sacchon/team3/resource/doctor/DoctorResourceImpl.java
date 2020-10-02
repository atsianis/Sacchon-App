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
import org.jetbrains.annotations.NotNull;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class DoctorResourceImpl extends ServerResource implements DoctorResource {
    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private long doctor_id;
    private DoctorRepository doctorRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease(){
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            doctorRepository = new DoctorRepository(em);
            doctor_id = Long.parseLong(getAttribute("doctor_id"));
        } catch (Exception e) {
            doctor_id = -1;
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
            Optional<Doctors> opDoctor = doctorRepository.findById(doctor_id);
            setExisting(opDoctor.isPresent());
            if (!isExisting()) {
                LOGGER.config("doctor id does not exist:" + doctor_id);
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

    // UPDATE Doctor
    @Override
    public ResponseRepresentation<DoctorRepresentation> updateDoctor(CreatedOrUpdatedDoctorRepresentation doctorRepresentationIn) {
        LOGGER.finer("Update a doctor.");
        // Check given entity
        try {
            ResourceValidator.notNull(doctorRepresentationIn);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }

        LOGGER.finer("Doctor checked");
        try {
            Optional<Doctors> doctorOut = doctorRepository.findById(doctor_id);
            setExisting(doctorOut.isPresent());
            Doctors doctorToBePersisted ;
            // If patient exists, we update it.
            if (isExisting()) {
                LOGGER.finer("Update patient.");
                doctorToBePersisted = getDoctorToBePersisted(doctorRepresentationIn, doctorOut);
                // Update patient in DB and retrieve the new one.
                doctorOut = doctorRepository.update(doctorToBePersisted);
                // Check if retrieved patient is not null : if it is null it
                // means that the id is wrong.
                if (!doctorOut.isPresent()) {
                    LOGGER.finer("Patient does not exist.");
                    return new ResponseRepresentation<>(404, "SQL Exception", null);
                }
            } else {
                LOGGER.finer("Patient does not exist.");
                return new ResponseRepresentation<>(404, "Something went wrong", null);
            }
            LOGGER.finer("Patient successfully updated.");
            return new ResponseRepresentation<>(200, "Doctor created", new DoctorRepresentation(doctorOut.get()));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
    }

    @NotNull
    private Doctors getDoctorToBePersisted(CreatedOrUpdatedDoctorRepresentation doctorRepresentation, Optional<Doctors> patientOut) {
        Doctors doctor = patientOut.get();
        if (!(doctorRepresentation.getPassword()==null))
            doctor.setPassword((doctorRepresentation.getPassword()));
        if (!(doctorRepresentation.getFirstName()==null))
            doctor.setFirstName(doctorRepresentation.getFirstName());
        if (!(doctorRepresentation.getLastName()==null))
            doctor.setLastName(doctorRepresentation.getLastName());
        if (!(doctorRepresentation.getEmail()==null))
            doctor.setEmail(doctorRepresentation.getEmail());

        return doctor;
    }
}
