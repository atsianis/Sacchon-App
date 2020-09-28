package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Put;

public interface AddCommentResource {
    @Put("json")
    public ResponseRepresentation<ConsultationRepresentation> addCommentConsultation(ConsultationRepresentation consultationRepresentation);
}
