package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ConsultationRepresentation {
    private Date timeCreated;
    private String comment;
    private Patients patient;
    private Date seenByPatient;
    private Doctors doctor;
    private String uri;

    public ConsultationRepresentation(Consultations consultation) {
        if (consultation != null) {
            timeCreated = consultation.getTimeCreated();
            comment = consultation.getComment();
            seenByPatient = consultation.getSeenByPatient();
            doctor = consultation.getDoctor();
            patient = consultation.getPatient();
            uri = "http://localhost:9000/v1/consultation/" + consultation.getId();
        }
    }

    public Consultations createConsultation() {
        Consultations c = new Consultations();
        c.setComment(comment);
        c.setTimeCreated(timeCreated);
        c.setDoctor(doctor);
        c.setSeenByPatient(seenByPatient);
        c.setPatient(patient);
        return c;
    }

}
