package com.pfizer.sacchon.team3.resource.softDeletes;

import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Put;

public interface SoftDeleteDoctor {
    @Put("json")
    public ResponseRepresentation<DoctorRepresentation> softDelete();
}
