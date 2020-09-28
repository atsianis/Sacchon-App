package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface PatientRecordsList {

    @Get("json")
    public ResponseRepresentation<List<PatientRecordRepresentation>> getAllPatientRecords();

    @Post("json")
    public ResponseRepresentation<PatientRecordRepresentation> storeData(PatientRecordRepresentation patientRecordRepresentation);
}
