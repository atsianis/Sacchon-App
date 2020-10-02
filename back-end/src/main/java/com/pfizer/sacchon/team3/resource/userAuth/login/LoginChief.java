package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.representation.ChiefRepresentation;
import com.pfizer.sacchon.team3.representation.LoginRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Post;

public interface LoginChief {
    @Post("json")
    ResponseRepresentation<ChiefRepresentation> loginChief(LoginRepresentation loginRepresentation);
}
