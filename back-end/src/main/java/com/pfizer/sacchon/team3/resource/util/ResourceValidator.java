package com.pfizer.sacchon.team3.resource.util;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;

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
     * Checks that the given patient is valid.
     *
     * @param patientRepresentation
     * @throws BadEntityException
     */
    public static void validate(PatientRepresentation patientRepresentation)
            throws BadEntityException {
        if ( patientRepresentation.getLastName() == null || patientRepresentation.getFirstName() == null) {
            throw new BadEntityException(
                    "patient's name cannot be null");
        }
    }

    public static void validate(DoctorRepresentation doctorRepresentation)
            throws BadEntityException {
        if ( doctorRepresentation.getLastName() == null || doctorRepresentation.getFirstName() == null
                || doctorRepresentation.getEmail() == null || doctorRepresentation.getPassword() == null ) {
            throw new BadEntityException(
                    "doctor creation demands name and credentials");
        }
    }
}
