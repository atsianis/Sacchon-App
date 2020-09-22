package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.model.PatientRecords;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String gender;
    private List<PatientRecords> patientRecords = new ArrayList<>();
    private String uri;


    public PatientRepresentation(Patients patient) {
        if (patient != null) {
            firstName = patient.getFirstName();
            lastName = patient.getLastName();
            email = patient.getEmail();
            password = patient.getPassword();
            dob = patient.getDob();
            canBeExamined = patient.isCanBeExamined();
            isDeleted = patient.isDeleted();
            lastActive = patient.getLastActive();
            gender = patient.getGender();
            patientRecords = patient.getPatientRecords();
            uri = "http://localhost:9000/v1/patient/" + patient.getId();
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
        p.setGender(gender);
        return p;
    }
}
