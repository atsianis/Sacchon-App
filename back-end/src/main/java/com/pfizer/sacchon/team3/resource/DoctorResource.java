package com.pfizer.sacchon.team3.resource;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public interface DoctorResource {
    @Get("json")
    public com.pfizer.sacchon.team3.representation.DoctorRepresentation getDoctor() throws NotFoundException;

    @Delete
    public void remove() throws NotFoundException;

    @Put("json")
    public com.pfizer.sacchon.team3.representation.DoctorRepresentation store(com.pfizer.sacchon.team3.representation.DoctorRepresentation productReprIn)
            throws NotFoundException, BadEntityException;

    @Post("json")
    public DoctorRepresentation add(DoctorRepresentation companyReprIn)
            throws BadEntityException;
}
