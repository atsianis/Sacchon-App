package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AllAvailablePatientListResourceImpl extends ServerResource implements AllAvailablePatientsList {
    public static final Logger LOGGER = Engine.getLogger(AllAvailablePatientListResourceImpl.class);
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public List<PatientRepresentation> getAllAvailablePatients() throws NotFoundException {
        LOGGER.finer("Select available patients.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        try {
            List<Patients> patients = patientRepository.findAllAvailablePatients();
            List<PatientRepresentation> result = new ArrayList<>();
            for (Patients patient : patients) {
                Hibernate.initialize(patient.getPatientRecords());
                result.add(new PatientRepresentation(patient));
            }
            return result;
        } catch (Exception e) {
            throw new NotFoundException("patients not found");
        }
    }
}
