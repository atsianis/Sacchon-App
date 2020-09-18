package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Consultation;
import com.pfizer.sacchon.team3.model.Patient;
import com.pfizer.sacchon.team3.model.PatientRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PatientRecordRepresentation {
    private float sacchon;
    private float calories;
    private Date timeCreated;
    private Patient patient;
    private Consultation consultation;
    /**
     * The URL of this resource.
     */
    private String uri;

    public PatientRecordRepresentation(PatientRecord patRec){}
}
