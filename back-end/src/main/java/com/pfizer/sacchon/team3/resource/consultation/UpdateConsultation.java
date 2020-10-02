package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedConsultRepresentation;
import org.restlet.resource.Put;

public interface UpdateConsultation {
    @Put("json")
    public ResponseRepresentation<ConsultationRepresentation> updateConsultation(CreatedOrUpdatedConsultRepresentation consultationRepresentation);
}
