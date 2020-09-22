package com.pfizer.sacchon.team3.resource.userAuth.login;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Chiefs;
import com.pfizer.sacchon.team3.repository.ChiefRepository;
import com.pfizer.sacchon.team3.representation.ChiefRepresentation;
import org.restlet.resource.Get;

public interface LoginChief {
    @Get("json")
    ChiefRepresentation loginChief() throws NotFoundException;
}
