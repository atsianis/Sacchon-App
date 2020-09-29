package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Put;

public interface SoftDeletePatient {
    @Put("json")
    public ResponseRepresentation<PatientRepresentation> softDelete();
}
