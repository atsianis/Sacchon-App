package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface DoctorListResource {

    @Get("json")
    public List<DoctorRepresentation> getDoctors() throws NotFoundException;
}
