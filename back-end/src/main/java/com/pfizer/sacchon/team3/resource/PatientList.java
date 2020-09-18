package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface PatientList {

    @Get("json")
    public List<PatientRepresentation> getAllPatients() throws NotFoundException;

    @Post("json")
    public PatientRepresentation add(PatientRepresentation patientRepresentation)
            throws BadEntityException;
    @Get("json")
    public List<PatientRepresentation> getAllAvailablePatients() throws NotFoundException;

}
