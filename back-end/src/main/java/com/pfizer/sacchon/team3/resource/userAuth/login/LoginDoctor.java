package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Post;

public interface LoginDoctor {
    @Post("json")
    public ResponseRepresentation<DoctorRepresentation> loginDoctor(LoginRepresentation loginRepresentation);
}
