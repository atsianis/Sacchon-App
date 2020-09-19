package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientRecordsImpl {
    @Get("json")
    public List<PatientRecordRepresentation> getPatientRecords(PatientRepresentation patientRepresentation) throws NotFoundException;
}
