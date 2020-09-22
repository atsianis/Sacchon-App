package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class DoctorRepresentation {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date lastActive;
    private boolean isDeleted;
    private List<Patients> patients = new ArrayList<>();
    private List<Consultations> consultations = new ArrayList<>();
    private String uri;

    public DoctorRepresentation(Doctors doctor) {
        if (doctor != null) {
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            email = doctor.getEmail();
            password = doctor.getPassword();
            lastActive = doctor.getLastActive();
            patients = doctor.getPatients();
            consultations = doctor.getConsultations();
            isDeleted = doctor.isDeleted();
            uri = "http://localhost:9000/v1/doctor/" + doctor.getId();
        }
    }

    public Doctors createDoctor() {
        Doctors doctor = new Doctors();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setEmail(email);
        doctor.setPassword(password);
        doctor.setLastActive(lastActive);
        doctor.setDeleted(isDeleted);
        return doctor;
    }
}
