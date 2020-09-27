package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Get;

public interface LoginPatient {
    @Get("json")
    public PatientRepresentation loginPatient (String email, String password) throws NotFoundException;
}
