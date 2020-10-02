package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface InactiveDoctors {
    @Get("json")
    public ResponseRepresentation<List<DoctorRepresentation>> inactiveDoctors();
}
