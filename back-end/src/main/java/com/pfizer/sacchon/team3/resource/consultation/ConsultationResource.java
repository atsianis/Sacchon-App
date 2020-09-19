package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import org.restlet.resource.Get;

public interface ConsultationResource {

    @Get("json")
    public ConsultationRepresentation getConsultation() throws NotFoundException;
}
