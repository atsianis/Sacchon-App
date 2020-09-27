package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import org.restlet.resource.Put;

public interface DoctorSelectionResource {

    @Put("json")
    public boolean selectPatient() throws NotFoundException;
}
