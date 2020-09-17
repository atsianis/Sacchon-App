package com.pfizer.sacchon.team3.representation;

import com.pfizer.sacchon.team3.model.Doctor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.Date;

@Data
@NoArgsConstructor
public class DoctorRepresentation {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date lastActive;
    private String uri;

    public DoctorRepresentation(Doctor doctor) {
        if (doctor != null) {
            firstName = doctor.getFirstName();
            lastName = doctor.getLastName();
            email = doctor.getEmail();
            password = doctor.getPassword();
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
            uri = "http://localhost:9000/v1/doctor/" + doctor.getId();
        }


    }

    public Doctor createDoctor() {
        Doctor doctor =  new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastActive(lastActive);
        doctor.setLastName(lastName);
        doctor.setEmail(email);
        //doctor.setPassword(password);
        return doctor;
    }
}
