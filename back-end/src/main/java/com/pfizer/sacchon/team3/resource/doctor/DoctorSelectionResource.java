package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.representation.SelectionRepresentation;
import org.restlet.resource.Put;

public interface DoctorSelectionResource {

    @Put("json")
    public ResponseRepresentation<Boolean> selectPatient(SelectionRepresentation selection);
}
