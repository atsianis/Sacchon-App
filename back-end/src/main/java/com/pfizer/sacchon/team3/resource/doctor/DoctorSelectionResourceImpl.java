package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.representation.SelectionRepresentation;
import org.jetbrains.annotations.NotNull;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class DoctorSelectionResourceImpl extends ServerResource implements DoctorSelectionResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorSelectionResourceImpl.class);
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Doctor's patients resource starts");
        try {
            doctorRepository = new DoctorRepository(em);
            patientRepository = new PatientRepository(em);
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.info("Doctor selects Patient resource ends");
    }

    @Override
    public ResponseRepresentation<Boolean> selectPatient(SelectionRepresentation selection) {
        LOGGER.finer("Doctor selects a patient starts");
        try {
            Doctors doctor;
            Patients patient;
            Optional<Doctors> opDoctor = doctorRepository.findById(selection.getDoctor_id());
            Optional<Patients> opPatient = patientRepository.findById(selection.getPatient_id());

            setExisting(opDoctor.isPresent());
            if (isExisting())
                doctor = opDoctor.get();
            else
                return new ResponseRepresentation<>(404, "Doctor not found", false);

            setExisting(opPatient.isPresent());
            if (isExisting())
                patient = opPatient.get();
            else
                return new ResponseRepresentation<>(404, "Patient not found", false);

            if (patient.getDoctor() == null)
                return getBooleanResponseRepresentation(doctor, patient);
            else
                return new ResponseRepresentation<>(401, "Patient already has a doctor", false);

        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Not found", false);
        }
    }

    @NotNull
    private ResponseRepresentation<Boolean> getBooleanResponseRepresentation(Doctors doctor, Patients patient) {
        patient.setDoctor(doctor);
        try {
            patientRepository.update(patient);
            return new ResponseRepresentation<>(200, "Patient successfully selected", true);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Update not done", false);
        }
    }
}
