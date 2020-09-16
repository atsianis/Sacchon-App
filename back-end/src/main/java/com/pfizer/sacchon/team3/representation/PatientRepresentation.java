package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Patient;
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
    private enum  gender{MALE,FEMALE};
    private String uri;


    public PatientRepresentation(
            Patient patient) {
        if (patient != null) {
            firstName = patient.getFirstName();
            lastName = patient.getLastName();
            email = patient.getEmail();
            password = patient.getPassword();
            dob = patient.getDob();
            canBeExamined = patient.isCanBeExamined();
            isDeleted = patient.isDeleted();
            lastActive = patient.getLastActive();
            uri = "http://localhost:9000/v1/patient/" + patient.getId();
        }
    }

    public Patient createPatient() {
        Patient p = new Patient();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setEmail(email);
        p.setPassword(password);
        p.setDob(dob);
        p.setCanBeExamined(canBeExamined);
        p.setDeleted(isDeleted);
        p.setLastActive(lastActive);
        return p;
    }
}
