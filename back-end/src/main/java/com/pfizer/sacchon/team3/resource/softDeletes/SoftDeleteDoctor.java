package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import org.restlet.resource.Put;

public interface SoftDeleteDoctor {
    @Put("json")
    public DoctorRepresentation softDelete (DoctorRepresentation doctorRepresentation) throws NotFoundException, BadEntityException;
}
