package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Post;

public interface LoginPatient {
    @Post("json")
    public ResponseRepresentation<PatientRepresentation> loginPatient(LoginRepresentation loginRepresentation);
}
