package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Doctor;

import javax.print.Doc;
import java.util.Date;

public class DoctorRepresentation {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date lastActive;
    private String uri;

    public DoctorRepresentation(
            Doctor doctor) {
        if (doctor != null) {
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            email = doctor.getEmail();
            password = doctor.getPassword();
            lastActive = doctor.getLastActive();
            uri = "http://localhost:9000/v1/doctor/" + doctor.getId();
        }
    }

    public Doctor createDoctor() {
        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setEmail(email);
        doctor.setPassword(password);
        doctor.setLastActive(lastActive);
        return doctor;
    }
}
