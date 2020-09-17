package com.pfizer.sacchon.team3.resource.util;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;

public class ResourceValidator {
    /**
     * Checks that the given entity is not null.
     *
     * @param entity
     *            The entity to check.
     * @throws BadEntityException
     *             In case the entity is null.
     */
    public static void notNull(Object entity) throws BadEntityException {
        if (entity == null) {
            throw new BadEntityException("No input entity");
        }
    }

    /**
     * Checks that the given company is valid.
     *
     * @param doctorRepresentation
     * @throws BadEntityException
     */
    public static void validate(DoctorRepresentation doctorRepresentation)
            throws BadEntityException {
        if (doctorRepresentation.getFirstName()==null) {
            throw new BadEntityException(
                    "doctor name cannot be null");
        }
    }
}
