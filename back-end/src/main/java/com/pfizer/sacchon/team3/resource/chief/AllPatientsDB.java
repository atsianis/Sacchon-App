package com.pfizer.sacchon.team3.resource.chief;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface AllPatientsDB {
    @Get("json")
    public List<PatientRepresentation> getAllPatientsDB() throws NotFoundException;
}
