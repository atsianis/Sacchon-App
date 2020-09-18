package com.pfizer.sacchon.team3.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Patients {

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
    @JoinColumn(name= "doctors_id")
    private Doctors doctors;
    @OneToMany(mappedBy = "patients")
    private List<PatientRecords> patientRecords = new ArrayList<>();
}
