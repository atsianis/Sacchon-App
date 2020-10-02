package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AllConsultablePatientListResourceImpl extends ServerResource implements AllConsultablePatientsList {
    public static final Logger LOGGER = Engine.getLogger(AllConsultablePatientListResourceImpl.class);
    private PatientRepository patientRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising patient resource starts");
        try {
            patientRepository = new PatientRepository(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising patient resource ends");
    }

    @Override
    public ResponseRepresentation<List<PatientRepresentation>> getAllConsultablePatients() throws NotFoundException {
        LOGGER.finer("Select consultable patients.");
        try {
            List<Patients> patients = patientRepository.findAllConsultablePatients();
            List<PatientRepresentation> result = new ArrayList<>();
            for (Patients patient : patients) {
                Hibernate.initialize(patient.getPatientRecords());
                result.add(new PatientRepresentation(patient));
            }

            return new ResponseRepresentation<>(200, "Patients retrieved", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Patients not found", null);
        }
    }
}
