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
    private long patient_id;
    private Date seenByPatient;
    private long doctor_id;
    private long id;

    public ConsultationRepresentation(Consultations consultation) {
        if (consultation != null) {
            timeCreated = consultation.getTimeCreated();
            comment = consultation.getComment();
            seenByPatient = consultation.getSeenByPatient();
            if (consultation.getDoctor()!=null){
                doctor_id = consultation.getDoctor().getId();
            }else{
                doctor_id = 0;
            }
            if (consultation.getPatient()!=null){
                patient_id = consultation.getPatient().getId();
            }else{
                patient_id = 0;
            }
            id = consultation.getId();
        }
    }

    public Consultations createConsultation() {
        Consultations c = new Consultations();
        c.setComment(this.comment);
        c.setTimeCreated(this.timeCreated);
        //c.setDoctor(this.doctor);
        //c.setPatient(this.patient);
        c.setSeenByPatient(this.seenByPatient);
        c.setId(this.id);

        return c;
    }

}
