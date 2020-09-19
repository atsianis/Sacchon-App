package com.pfizer.sacchon.team3.resource.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import org.restlet.resource.Post;

public interface RegisterDoctor {
    @Post("json")
    public DoctorRepresentation add(DoctorRepresentation companyReprIn) throws BadEntityException;
}
