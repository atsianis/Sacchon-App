package com.pfizer.sacchon.team3.resource.userAuth.register;

import com.pfizer.sacchon.team3.representation.CreatedOrUpdatedDoctorRepresentation;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Post;

public interface RegisterDoctor {
    @Post("json")
    public ResponseRepresentation<DoctorRepresentation> add(CreatedOrUpdatedDoctorRepresentation doctorRepresentation);
}
