package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface ConsultablePatientsByDoctor {

    @Get
    public ResponseRepresentation<List<PatientRepresentation>> getConsultablePatientsByDoctor();
}
