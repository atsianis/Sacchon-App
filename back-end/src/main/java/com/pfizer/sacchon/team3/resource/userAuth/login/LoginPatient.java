package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.exception.WrongCredentials;
import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Post;

public interface LoginPatient {
    @Post("json")
    public PatientRepresentation loginPatient (LoginRepresentation loginRepresentation) throws NotFoundException, WrongCredentials;
}
