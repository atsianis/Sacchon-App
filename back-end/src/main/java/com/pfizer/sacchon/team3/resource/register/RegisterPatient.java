package com.pfizer.sacchon.team3.resource.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Post;

public interface RegisterPatient {
    @Post("json")
    public PatientRepresentation add(PatientRepresentation patientRepresentation) throws BadEntityException;
}
