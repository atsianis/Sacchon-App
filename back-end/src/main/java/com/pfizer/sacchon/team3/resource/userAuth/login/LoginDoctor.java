package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.exception.WrongCredentials;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import org.restlet.resource.Post;

public interface LoginDoctor {
    @Post("json")
    public DoctorRepresentation loginDoctor(LoginRepresentation loginRepresentation) throws NotFoundException, WrongCredentials;
}
