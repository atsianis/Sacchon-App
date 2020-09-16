package com.pfizer.sacchon.team3.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date timeCreated;
    private String comment;
    private Date seenByPatient;
    @OneToMany(mappedBy = "consultation")
    private List<PatientRecord> patientRecords = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


}
