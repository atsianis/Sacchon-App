package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MyPatientsResourceImpl extends ServerResource implements Mypatients {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Doctor's patients resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
        }
        LOGGER.info("Doctor's patients resource ends");
    }

    @Override
    public List<PatientRepresentation> myPatients(DoctorRepresentation doctorRepresentation) throws NotFoundException {
        LOGGER.finer("Select my patients.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        try {
            Doctors d = doctorRepresentation.createDoctor();
            List<Patients> patients = doctorRepository.myPatients(d);
            List<PatientRepresentation> result = new ArrayList<>();
            patients.forEach(patient -> result.add(new PatientRepresentation(patient)));

            return result;
        } catch (Exception e) {
            throw new NotFoundException("products not found");
        }
    }
}

