package com.pfizer.sacchon.team3.resource.patientRecord;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.PatientRecordRepository;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import java.util.List;

public interface PatientRecordResource {

    @Get("json")
    public PatientRecordRepresentation getRecord() throws NotFoundException, BadEntityException;

    @Delete
    public void remove() throws NotFoundException;

    @Put("json")
    public PatientRecordRepresentation updateRecord(PatientRecordRepresentation patientRecordRepresentation, Patients patient)
            throws NotFoundException, BadEntityException;
}
