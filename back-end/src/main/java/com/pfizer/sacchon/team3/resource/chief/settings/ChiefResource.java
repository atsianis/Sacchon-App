package com.pfizer.sacchon.team3.resource.chief.settings;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.ChiefRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Put;

public interface ChiefResource {
    @Put("json")
    public ResponseRepresentation<ChiefRepresentation> update(ChiefRepresentation chiefRepresentation);
}
