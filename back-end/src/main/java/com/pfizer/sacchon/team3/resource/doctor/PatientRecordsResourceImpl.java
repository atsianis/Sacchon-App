package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatientRecordsResourceImpl extends ServerResource implements PatientRecordsImpl {

    public static final Logger LOGGER = Engine.getLogger(PatientRecordsResourceImpl.class);
    private DoctorRepository doctorRepository;
    private long id;

    @Override
    protected void doInit() {
        LOGGER.info("Patients records resource starts");
        try {
            id = Long.parseLong(getAttribute("id"));
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Patients records resource ends");
    }

    public ResponseRepresentation<List<PatientRecordRepresentation>> getPatientRecords(PatientRepresentation patientRepresentation){
        LOGGER.finer("Select patient's records.");
        try {
            List<PatientRecords> patientRecs = doctorRepository.patientRecords(id);
            List<PatientRecordRepresentation> result = new ArrayList<>();
            patientRecs.forEach(patientRec -> result.add(new PatientRecordRepresentation(patientRec)));

            return new ResponseRepresentation<>(200,"Records retrieved",result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404,"Not found",null);
        }
    }
}
