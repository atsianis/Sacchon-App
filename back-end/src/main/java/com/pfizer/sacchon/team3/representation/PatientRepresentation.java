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
    private enum  gender{MALE,FEMALE};
    private String uri;


    public PatientRepresentation(
            Patients patients) {
        if (patients != null) {
            firstName = patients.getFirstName();
            lastName = patients.getLastName();
            email = patients.getEmail();
            password = patients.getPassword();
            dob = patients.getDob();
            canBeExamined = patients.isCanBeExamined();
            isDeleted = patients.isDeleted();
            lastActive = patients.getLastActive();
            uri = "http://localhost:9000/v1/patient/" + patients.getId();
        }
    }

    public Patients createPatient() {
        Patients p = new Patients();
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
