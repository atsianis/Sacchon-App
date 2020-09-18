package com.pfizer.sacchon.team3.resource.DoctorRes;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patient;
import com.pfizer.sacchon.team3.model.PatientRecord;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatientRecordsResourceImpl extends ServerResource implements PatientRecordsImpl {

    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Patients records resource starts");
        try {
            doctorRepository = new DoctorRepository (JpaUtil.getEntityManager());
            patientRepository = new PatientRepository (JpaUtil.getEntityManager());
        }
        catch(Exception e)
        {}
        LOGGER.info("Patients records resource ends");
    }

    public List<PatientRecordRepresentation> getPatientRecords(PatientRepresentation patientRepresentation) throws NotFoundException{
        LOGGER.finer("Select patient's records.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        try{
            Patient pat = patientRepresentation.createPatient();
            List<PatientRecord> patientRecs = doctorRepository.patientRecords(pat);
            List<PatientRecordRepresentation> result = new ArrayList<>();
            patientRecs.forEach(patientRec -> result.add(new PatientRecordRepresentation(patientRec)));

            return result;
        }
        catch(Exception e)
        {
            throw new NotFoundException("Patient Records not found");
        }
    }
}
