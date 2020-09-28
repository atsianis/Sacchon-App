package com.pfizer.sacchon.team3.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Doctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private Date lastActive;
    private boolean isDeleted;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    @JsonManagedReference
    private List<Patients> patients = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    @JsonManagedReference
    private List<Consultations> consultations = new ArrayList<>();
}
