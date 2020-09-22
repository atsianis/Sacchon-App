package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Put;

public interface SoftDeletePatient {
    @Put("json")
    public PatientRepresentation softDelete(PatientRepresentation patientRepresentation) throws NotFoundException, BadEntityException;
}
