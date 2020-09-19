package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Get;

import java.util.List;

public interface Mypatients {
    @Get("json")
    public List<PatientRepresentation> myPatients(DoctorRepresentation doctorRepresentation) throws NotFoundException;
}
