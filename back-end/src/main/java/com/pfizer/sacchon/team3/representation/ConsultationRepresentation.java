package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Consultation;
import com.pfizer.sacchon.team3.model.Doctor;
import com.pfizer.sacchon.team3.model.Patient;
import com.pfizer.sacchon.team3.model.PatientRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ConsultationRepresentation {

    private Date timeCreated;
    private String comment;
    private Date seenByPatient;
    private List<PatientRecord> patientRecords = new ArrayList<>();
    private Doctor doctor;
    private String uri;

    public ConsultationRepresentation(Consultation consultation) {
        if (consultation != null) {
            timeCreated = consultation.getTimeCreated();
            comment = consultation.getComment();
            seenByPatient = consultation.getSeenByPatient();
            patientRecords = consultation.getPatientRecords();
            doctor = consultation.getDoctor();
            uri = "http://localhost:9000/v1/consultation/" + consultation.getId();
        }
    }

    public Consultation createConsultation() {
        Consultation c = new Consultation();
        c.setComment(comment);
        c.setTimeCreated(timeCreated);
        c.setDoctor(doctor);
        c.setPatientRecords(patientRecords);
        c.setSeenByPatient(seenByPatient);
        return c;
    }
}
