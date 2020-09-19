package com.pfizer.sacchon.team3.resource.chief;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface AllDoctorsList {
    @Get("json")
    public List<DoctorRepresentation> getDoctors() throws NotFoundException;
}
