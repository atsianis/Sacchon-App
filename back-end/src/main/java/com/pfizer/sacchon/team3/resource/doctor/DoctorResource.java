package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedDoctorRepresentation;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface DoctorResource {
    @Get("json")
    public ResponseRepresentation<DoctorRepresentation> getDoctor();

    @Put("json")
    public ResponseRepresentation<DoctorRepresentation> updateDoctor(CreatedOrUpdatedDoctorRepresentation doctorReprIn);
}
