package com.pfizer.sacchon.team3.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Consultations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date timeCreated;
    private String comment;
    private Date seenByPatient;
    @OneToMany(mappedBy = "consultations")
    private List<PatientRecords> patientRecords = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "doctors_id")
    private Doctors doctors;


}
