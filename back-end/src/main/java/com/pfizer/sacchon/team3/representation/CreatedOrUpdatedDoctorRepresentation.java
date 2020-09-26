package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Doctors;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CreatedOrUpdatedDoctorRepresentation {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date lastActive;
    private boolean isDeleted;
    private long id;

    public CreatedOrUpdatedDoctorRepresentation(Doctors doctor) {
        if (doctor != null) {
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            email = doctor.getEmail();
            password = doctor.getPassword();
            lastActive = doctor.getLastActive();
            isDeleted = doctor.isDeleted();
            id= doctor.getId();
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
        doctor.setId(id);
        return doctor;
    }
}
