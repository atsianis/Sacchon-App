package com.pfizer.sacchon.team3.resource.patientRecord;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.representation.PatientRecordRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import java.util.List;

public interface PatientRecordResource {

    @Get("json")
    public ResponseRepresentation<PatientRecordRepresentation> getRecord() throws NotFoundException, BadEntityException;

    @Delete
    public ResponseRepresentation<PatientRecordRepresentation> remove() throws NotFoundException;

    @Put("json")
    public ResponseRepresentation<PatientRecordRepresentation> updateRecord(PatientRecordRepresentation patientRecordRepresentation);
}
