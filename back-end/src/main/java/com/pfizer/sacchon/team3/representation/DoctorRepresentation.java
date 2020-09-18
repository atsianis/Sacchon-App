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
    private List<Patients> patients = new ArrayList<>();
    private List<Consultations> consultations = new ArrayList<>();
    private String uri;

    public DoctorRepresentation(Doctors doctor) {
        if (doctor != null) {
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            email = doctor.getEmail();
            //password = doctor.getPassword();
//            System.out.println(doctor.getLastActive());
//            long epoch = doctor.getLastActive().getTime();
//            System.out.println("epoch: "+epoch);
//            Instant instant = Instant.ofEpochSecond(epoch);
//            System.out.println("instant: "+instant);
//            ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
//            System.out.println("zdt: "+ zdt);
//            System.out.println(zdt.toInstant());
//            lastActive = Date.from(zdt.toInstant());
            lastActive = doctor.getLastActive();
            consultations = doctor.getConsultations();
            uri = "http://localhost:9000/v1/doctor/" + doctor.getId();
        }


    }

    public Doctors createDoctor() {
        Doctors doctor =  new Doctors();
        doctor.setFirstName(firstName);
        doctor.setLastActive(lastActive);
        doctor.setLastName(lastName);
        doctor.setEmail(email);
        doctor.setPassword(password);
        return doctor;
    }
}
