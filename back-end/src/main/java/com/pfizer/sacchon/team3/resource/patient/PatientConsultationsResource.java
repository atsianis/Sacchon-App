package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientConsultationsResource {

    @Get("json")
    List<ConsultationRepresentation> getPatientsConsultations() throws NotFoundException;
}
