package com.pfizer.sacchon.team3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private Date dob;
    private boolean canBeExamined;
    private boolean isDeleted;
    private Date timeCreated;
    private Date lastActive;
    private String gender;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private Doctors doctor;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @JsonManagedReference
    private List<PatientRecords> patientRecords = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @JsonManagedReference
    private List<Consultations> consultations = new ArrayList<>();
}