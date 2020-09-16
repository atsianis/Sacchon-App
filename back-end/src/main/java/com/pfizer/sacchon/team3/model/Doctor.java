package com.pfizer.sacchon.team3.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String password;
    private Date lastActive;
    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<Consultation> consultations = new ArrayList<>();
}
