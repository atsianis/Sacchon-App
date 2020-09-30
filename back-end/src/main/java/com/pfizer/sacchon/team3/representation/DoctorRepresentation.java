package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Doctors;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class DoctorRepresentation {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date lastActive;
    private boolean isDeleted;
    private long id;

    public DoctorRepresentation(Doctors doctor) {
        if (doctor != null) {
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            email = doctor.getEmail();
            password = doctor.getPassword();
            lastActive = doctor.getLastActive();
            isDeleted = doctor.isDeleted();
            id = doctor.getId();
        }
    }

    public Doctors createDoctor() {
        Doctors doctor = new Doctors();
        doctor.setFirstName(this.firstName);
        doctor.setLastName(this.lastName);
        doctor.setEmail(this.email);
        doctor.setPassword(this.password);
        doctor.setLastActive(this.lastActive);
        doctor.setDeleted(this.isDeleted);
        doctor.setId(this.id);

        return doctor;
    }
}
