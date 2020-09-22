package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import org.restlet.resource.Get;

public interface LoginDoctor {
    @Get("json")
    public DoctorRepresentation loginDoctor() throws NotFoundException;
}
