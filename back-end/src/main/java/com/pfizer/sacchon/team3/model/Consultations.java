package com.pfizer.sacchon.team3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Consultations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date timeCreated;
    private String comment;
    private Date seenByPatient;
    @ManyToOne
    @JoinColumn(name = "patients_id")
    @JsonBackReference
    private Patients patient;
    @ManyToOne
    @JoinColumn(name = "doctors_id")
    @JsonBackReference
    private Doctors doctor;
}
