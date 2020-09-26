package com.pfizer.sacchon.team3.resource.userAuth.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedDoctorRepresentation;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import org.restlet.resource.Post;

public interface RegisterDoctor {
    @Post("json")
    public DoctorRepresentation add(CreatedOrUpdatedDoctorRepresentation doctorRepresentation) throws BadEntityException;
}
