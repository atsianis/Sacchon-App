package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface InactivePatients {
    @Get("json")
    public List<PatientRepresentation> inactivePatients() throws NotFoundException;
}
