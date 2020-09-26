package com.pfizer.sacchon.team3.resource.userAuth.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedPatientRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Post;

public interface RegisterPatient {
    @Post("json")
    public PatientRepresentation add(CreatedOrUpdatedPatientRepresentation patientRepresentation) throws BadEntityException;
}
