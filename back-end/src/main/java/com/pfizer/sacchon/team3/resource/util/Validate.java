package com.pfizer.sacchon.team3.resource.util;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.ChiefRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;

public class Validate<T> {

    public ResponseRepresentation checkValidation (T representation){
        try {
            ResourceValidator.notNull(representation);
            //ResourceValidator.validate(representation);
            return new ResponseRepresentation(200, "Entity perfect", representation);
        } catch (BadEntityException ex) {
            return new ResponseRepresentation<>(422, "Bad Entity", null);
        }
    }
}
