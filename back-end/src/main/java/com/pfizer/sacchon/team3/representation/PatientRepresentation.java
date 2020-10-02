package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Patients;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PatientRepresentation {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dob;
    private boolean canBeExamined;
    private boolean isDeleted;
    private Date lastActive;
    private Date timeCreated;
    private String gender;
    private long doctor_id;
    private long id;


    public PatientRepresentation(Patients patient) {
        if (patient != null) {
            if (patient.getDoctor() != null) {
                doctor_id = patient.getDoctor().getId();
            } else {
                doctor_id = 0;
            }
            firstName = patient.getFirstName();
            lastName = patient.getLastName();
            email = patient.getEmail();
            password = patient.getPassword();
            dob = patient.getDob();
            canBeExamined = patient.isCanBeExamined();
            isDeleted = patient.isDeleted();
            timeCreated = patient.getTimeCreated();
            lastActive = patient.getLastActive();
            gender = patient.getGender();
            id = patient.getId();
        }
    }

    public Patients createPatient() {
        Patients p = new Patients();
        p.setFirstName(this.firstName);
        p.setLastName(this.lastName);
        p.setEmail(this.email);
        p.setPassword(this.password);
        p.setDob(this.dob);
        p.setCanBeExamined(this.canBeExamined);
        p.setDeleted(this.isDeleted);
        p.setLastActive(this.lastActive);
        p.setGender(this.gender);
        p.setTimeCreated((this.timeCreated));
        p.setId(this.id);

        return p;
    }
}
