package com.pfizer.sacchon.team3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PatientRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float sacchon;
    private float calories;
    private Date timeCreated;
    @ManyToOne
    @JoinColumn(name ="patient_id")
    @JsonBackReference
    private Patient patient;
    @ManyToOne
    @JoinColumn(name ="consultation_id")
    @JsonBackReference
    private Consultation consultation;

}
