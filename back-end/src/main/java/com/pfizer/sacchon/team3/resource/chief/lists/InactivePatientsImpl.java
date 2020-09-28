package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InactivePatientsImpl extends ServerResource implements InactivePatients {


    public static final Logger LOGGER = Engine.getLogger(InactivePatientsImpl.class);
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

    public ResponseRepresentation<List<PatientRepresentation>> inactivePatients() {
        LOGGER.finer("Select inactive patients.");
        try {
            List<Patients> patients = patientRepository.findInactivePatients();
            List<PatientRepresentation> result = new ArrayList<>();
            for (Patients patient : patients) {
                Hibernate.initialize(patient);
                result.add(new PatientRepresentation(patient));
            }

            return new ResponseRepresentation<>(200, "Patients retrieved", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Patients not found", null);
        }
    }
}
