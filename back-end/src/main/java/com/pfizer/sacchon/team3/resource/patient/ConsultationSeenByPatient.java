package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Put;

public interface ConsultationSeenByPatient {

    @Put
    public ResponseRepresentation<ConsultationRepresentation> setConsultationAsSeen();
}
