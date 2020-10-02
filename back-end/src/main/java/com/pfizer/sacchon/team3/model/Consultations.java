package com.pfizer.sacchon.team3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Consultations implements Comparable<Consultations> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date timeCreated;
    private String comment;
    private Date seenByPatient;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patients patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private Doctors doctor;

    @Override
    public int compareTo(Consultations c) {
        return timeCreated.compareTo(c.getTimeCreated());
    }
}
