package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface InactivePatients {
    @Get("json")
    public ResponseRepresentation<List<PatientRepresentation>> inactivePatients();
}
