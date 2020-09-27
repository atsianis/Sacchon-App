package com.pfizer.sacchon.team3.resource.consultation;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.BadInsertionException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import org.restlet.resource.Put;

public interface AddCommentResource {
    @Put("json")
    public ConsultationRepresentation addCommentConsultation(ConsultationRepresentation consultationRepresentation) throws NotFoundException, BadEntityException, BadInsertionException;
}
