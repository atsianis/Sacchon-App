package com.pfizer.sacchon.team3.resource.doctor;

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

public class ConsultablePatientsByDoctorImpl extends ServerResource implements ConsultablePatientsByDoctor {
    public static final Logger LOGGER = Engine.getLogger(ConsultablePatientsByDoctorImpl.class);
    private PatientRepository patientRepository;
    long doctor_id;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising consultable patient resource starts");
        try {
            patientRepository = new PatientRepository(em);
            doctor_id = Long.parseLong(getAttribute("doctor_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising consultable patient resource ends");
    }

    @Override
    public ResponseRepresentation<List<PatientRepresentation>> getConsultablePatientsByDoctor() {
        LOGGER.finer("Select consultable patients.");
        try {
            List<Patients> patients = patientRepository.findConsultablePatientsByDoctor(doctor_id);
            List<PatientRepresentation> result = new ArrayList<>();
            for (Patients patient : patients) {
                result.add(new PatientRepresentation(patient));
            }

            return new ResponseRepresentation<>(200, "Patients retrieved", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Patients not found", null);
        }
    }
}
