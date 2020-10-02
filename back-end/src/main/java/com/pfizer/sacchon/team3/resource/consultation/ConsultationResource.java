package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Get;

public interface ConsultationResource {

    @Get("json")
    public ResponseRepresentation<ConsultationRepresentation> getConsultation();
}
