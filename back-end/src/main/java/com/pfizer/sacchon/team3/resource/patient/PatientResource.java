package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface PatientResource {
    @Get("json")
    public ResponseRepresentation<PatientRepresentation> getPatient();

    @Delete
    public ResponseRepresentation<PatientRepresentation> remove();

    @Put("json")
    public ResponseRepresentation<PatientRepresentation> store(PatientRepresentation patientRepresentation);
}
