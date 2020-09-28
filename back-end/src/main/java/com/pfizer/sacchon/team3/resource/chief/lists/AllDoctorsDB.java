package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface AllDoctorsDB {
    @Get("json")
    public List<DoctorRepresentation> getAllDoctorsDB() throws NotFoundException;
}
