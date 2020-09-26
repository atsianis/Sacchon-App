package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.PatientRecords;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PatientRecordRepresentation {
    private float sacchon;
    private float calories;
    private Date timeCreated;
    private long id;

    public PatientRecordRepresentation(PatientRecords patientRecords) {
        if (patientRecords != null) {
            sacchon = patientRecords.getSacchon();
            calories = patientRecords.getCalories();
            timeCreated = patientRecords.getTimeCreated();
            id = patientRecords.getId();
        }
    }

    public PatientRecords createPatientRecords() {
        PatientRecords patientRecords = new PatientRecords();
        patientRecords.setSacchon(this.sacchon);
        patientRecords.setCalories(this.calories);
        patientRecords.setTimeCreated(this.timeCreated);
        patientRecords.setId(this.id);

        return patientRecords;
    }
}
