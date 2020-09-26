package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Consultations;
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
    private List<Consultations> consultations = new ArrayList<>();
    private long id;


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
            consultations = patient.getConsultations();
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
        p.setPatientRecords(this.patientRecords);
        p.setConsultations(this.consultations);
        p.setId(this.id);

        return p;
    }
}
