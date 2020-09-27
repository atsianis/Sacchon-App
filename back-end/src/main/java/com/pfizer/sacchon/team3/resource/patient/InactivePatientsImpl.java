package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.PatientRecordRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.doctor.InactiveDoctorsImpl;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InactivePatientsImpl extends ServerResource implements InactivePatients {


    public static final Logger LOGGER = Engine.getLogger(InactiveDoctorsImpl.class);
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising inactive doctors starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising inactive patients resource ends");
    }

    public List<PatientRepresentation> inactivePatients() throws NotFoundException{
        LOGGER.finer("Select inactive patients.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_ADMIN);
        try {
            List<Patients> patients = patientRepository.findInactivePatients();
            List<PatientRepresentation> result = new ArrayList<>();
            for (Patients patient : patients) {
                Hibernate.initialize(patient);
                result.add(new PatientRepresentation(patient));
            }

            return result;
        } catch (Exception e) {
            throw new NotFoundException("patients not found");
        }
    }
}
