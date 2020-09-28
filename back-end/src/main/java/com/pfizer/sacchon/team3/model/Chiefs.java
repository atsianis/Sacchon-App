package com.pfizer.sacchon.team3.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Chiefs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
}
