package com.pfizer.sacchon.team3.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String password;
    private Date dob;
    private boolean canBeExamined;
    private boolean isDeleted;
    private Date lastActive;
    private enum  gender{MALE,FEMALE};
    @ManyToOne
    @JoinColumn(name= "doctor_id")
    private Doctor doctor;
    @OneToMany(mappedBy = "patient")
    private List<PatientRecord> patientRecords = new ArrayList<>();
}
