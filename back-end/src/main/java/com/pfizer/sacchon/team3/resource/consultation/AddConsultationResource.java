package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedConsultRepresentation;
import org.restlet.resource.Post;

public interface AddConsultationResource {
    @Post("json")
    public ResponseRepresentation<ConsultationRepresentation> addConsultation(CreatedOrUpdatedConsultRepresentation consultationRepresentation);
}
