package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientListResource {

    @Get("json")
    public List<PatientRepresentation> getPatients() throws NotFoundException;
}
