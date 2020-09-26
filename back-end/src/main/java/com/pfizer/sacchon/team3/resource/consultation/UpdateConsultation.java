package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.UpdateConsultationRepresentation;
import org.restlet.resource.Put;

import java.util.List;

public interface UpdateConsultation {
    @Put("json")
    public ConsultationRepresentation updateConsultation(UpdateConsultationRepresentation consultationRepresentation) throws NotFoundException, BadEntityException;
}
