package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.representation.ConsultationRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface DoctorConsultations {
    @Get("json")
    public ResponseRepresentation<List<ConsultationRepresentation>> getConsultationsByDoctor();
}
