package com.pfizer.sacchon.team3.resource.util;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.representation.*;

public class ResourceValidator {
    /**
     * Checks that the given entity is not null.
     *
     * @param entity The entity to check.
     * @throws BadEntityException In case the entity is null.
     */
    public static void notNull(Object entity) throws BadEntityException {
        if (entity == null)
            throw new BadEntityException("No input entity");
    }
    /**
     * Checks that the given doctor is valid.
     *
     * @param chiefRepresentation
     * @throws BadEntityException
     */
    public static void validate(ChiefRepresentation chiefRepresentation) throws BadEntityException {
        if (chiefRepresentation.getFirstName() == null || chiefRepresentation.getLastName() == null)
            throw new BadEntityException("chief name cannot be null");
    }
    /**
     * Checks that the given doctor is valid.
     *
     * @param doctorRepresentation
     * @throws BadEntityException
     */
    public static void validate(CreatedOrUpdatedDoctorRepresentation doctorRepresentation) throws BadEntityException {
        if (doctorRepresentation.getFirstName() == null || doctorRepresentation.getLastName() == null)
            throw new BadEntityException("doctor name cannot be null");
    }

    public static void validate(DoctorRepresentation doctorRepresentation) throws BadEntityException {
        if (doctorRepresentation.getFirstName() == null || doctorRepresentation.getLastName() == null)
            throw new BadEntityException("doctor name cannot be null");
    }

    /**
     * Checks that the given patient is valid.
     *
     * @param patientRepresentation
     * @throws BadEntityException
     */
    public static void validate(PatientRepresentation patientRepresentation) throws BadEntityException {
        if (patientRepresentation.getLastName() == null || patientRepresentation.getFirstName() == null)
            throw new BadEntityException("patient's name cannot be null");
    }

    public static void validate(CreatedOrUpdatedPatientRepresentation patientRepresentation) throws BadEntityException {
        if (patientRepresentation.getLastName() == null || patientRepresentation.getFirstName() == null)
            throw new BadEntityException("patient's name cannot be null");
    }
}
