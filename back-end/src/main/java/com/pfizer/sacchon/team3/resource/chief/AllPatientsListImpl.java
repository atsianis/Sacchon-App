package com.pfizer.sacchon.team3.resource.chief;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.resource.patient.PatientResourceImpl;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AllPatientsListImpl extends ServerResource implements AllPatientsList {
    public static final Logger LOGGER = Engine.getLogger(PatientResourceImpl.class);
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

    public List<PatientRepresentation> getAllPatients() throws NotFoundException {
        LOGGER.finer("Select all patients.");
        try {
            List<Patients> patients = patientRepository.findAllPatients();
            List<PatientRepresentation> result = new ArrayList<>();
            for (Patients patient : patients)
                if (!patient.isDeleted())
                    result.add(new PatientRepresentation(patient));
            //patients.forEach(patient -> result.add(new PatientRepresentation(patient)));

            return result;
        } catch (Exception e) {
            throw new NotFoundException("patients not found");
        }
    }
}
