package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;

import org.restlet.resource.Get;
import java.util.List;

public interface ConsultationListResource {

    @Get("json")
    public List<ConsultationRepresentation> getConsultations() throws NotFoundException;
}
