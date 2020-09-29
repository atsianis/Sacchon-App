package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface PatientResource {
    @Get("json")
    public ResponseRepresentation<PatientRepresentation> getPatient();

    @Put("json")
    public ResponseRepresentation<PatientRepresentation> updatePatient(PatientRepresentation patientRepresentation);
}
