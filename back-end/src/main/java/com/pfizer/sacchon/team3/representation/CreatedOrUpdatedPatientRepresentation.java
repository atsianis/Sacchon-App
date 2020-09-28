package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Patients;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CreatedOrUpdatedPatientRepresentation {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dob;
    private boolean canBeExamined;
    private boolean isDeleted;
    private Date lastActive;
    private String gender;
    private long id;

    public CreatedOrUpdatedPatientRepresentation(Patients patient) {
        if (patient != null) {
            firstName = patient.getFirstName();
            lastName = patient.getLastName();
            email = patient.getEmail();
            password = patient.getPassword();
            dob = patient.getDob();
            gender = patient.getGender();
            lastActive = patient.getLastActive();
            isDeleted = patient.isDeleted();
            canBeExamined = patient.isCanBeExamined();
            id = patient.getId();
        }
    }

    public Patients createPatients() {
        Patients patient = new Patients();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setEmail(email);
        patient.setPassword(password);
        patient.setDob(dob);
        patient.setGender(gender);
        patient.setLastActive(lastActive);
        patient.setDeleted(isDeleted);
        patient.setCanBeExamined(canBeExamined);
        patient.setId(id);

        return patient;
    }
}
