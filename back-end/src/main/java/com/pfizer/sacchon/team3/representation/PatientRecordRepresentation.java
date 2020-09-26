package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.PatientRecords;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PatientRecordRepresentation {
    private float glycose;
    private float carbs;
    private Date timeCreated;
    private String uri;

    public PatientRecordRepresentation(PatientRecords patientRecords) {
        if (patientRecords != null) {
            glycose = patientRecords.getGlycose();
            carbs = patientRecords.getCarbs();
            timeCreated = patientRecords.getTimeCreated();
        }
    }

    public PatientRecords createPatientRecords() {
        PatientRecords patientRecords = new PatientRecords();
        patientRecords.setGlycose(this.glycose);
        patientRecords.setCarbs(this.carbs);
        patientRecords.setTimeCreated(this.timeCreated);
        return patientRecords;
    }
}
