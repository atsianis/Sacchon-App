package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MyPatientsResourceImpl extends ServerResource implements Mypatients {

    public static final Logger LOGGER = Engine.getLogger(MyPatientsResourceImpl.class);
    private DoctorRepository doctorRepository;
    private long doctor_id;
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
            doctor_id = Long.parseLong(getAttribute("doctor_id"));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.info("Doctor's patients resource ends");
    }

    @Override
    public ResponseRepresentation<List<PatientRepresentation>> myPatients() {
        LOGGER.finer("Select my patients.");
        try {
            List<Patients> patients = doctorRepository.myPatients(doctor_id);
            List<PatientRepresentation> result = new ArrayList<>();
            patients.forEach(patient -> result.add(new PatientRepresentation(patient)));

            return new ResponseRepresentation<>(200, "Patients retrieved", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Not found", null);
        }
    }
}

