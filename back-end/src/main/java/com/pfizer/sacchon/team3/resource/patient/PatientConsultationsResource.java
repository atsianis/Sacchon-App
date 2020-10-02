package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface PatientConsultationsResource {

    @Get("json")
    ResponseRepresentation<List<ConsultationRepresentation>> getPatientsConsultations();
}
