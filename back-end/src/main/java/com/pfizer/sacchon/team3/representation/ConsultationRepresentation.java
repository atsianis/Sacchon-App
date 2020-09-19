package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.PatientRecords;
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
    private Doctors doctor;
    private List<PatientRecords> patientRecords = new ArrayList<>();
    private String uri;

    public ConsultationRepresentation(Consultations consultation) {
        if (consultation != null) {
            timeCreated = consultation.getTimeCreated();
            comment = consultation.getComment();
            seenByPatient = consultation.getSeenByPatient();
            doctor = consultation.getDoctor();
            patientRecords = consultation.getPatientRecords();
            uri = "http://localhost:9000/v1/consultation/" + consultation.getId();
        }
    }

    public Consultations createConsultation() {
        Consultations c = new Consultations();
        c.setComment(comment);
        c.setTimeCreated(timeCreated);
        c.setDoctor(doctor);
        c.setPatientRecords(patientRecords);
        c.setSeenByPatient(seenByPatient);
        return c;
    }
}
