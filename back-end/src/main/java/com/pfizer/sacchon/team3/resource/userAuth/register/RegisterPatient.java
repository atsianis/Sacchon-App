package com.pfizer.sacchon.team3.resource.userAuth.register;

import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedPatientRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Post;

public interface RegisterPatient {
    @Post("json")
    public ResponseRepresentation<PatientRepresentation> add(CreatedOrUpdatedPatientRepresentation patientRepresentation);
}
