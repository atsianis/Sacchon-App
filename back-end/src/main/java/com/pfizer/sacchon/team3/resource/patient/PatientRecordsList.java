package com.pfizer.sacchon.team3.resource.patient;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.BadInsertionException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.util.List;

public interface PatientRecordsList {

    @Get("json")
    public List<PatientRecordRepresentation> getAllPatientRecords() throws NotFoundException;

    @Post("json")
    public PatientRecordRepresentation storeData(PatientRecordRepresentation patientRecordRepresentation)
            throws NotFoundException, BadEntityException, BadInsertionException;
}
