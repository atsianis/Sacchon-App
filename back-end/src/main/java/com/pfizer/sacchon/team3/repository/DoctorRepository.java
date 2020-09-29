package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.exception.WrongCredentials;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DoctorRepository {

    private EntityManager entityManager;
    private WrongCredentials wrong_credentials;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private PatientRepository patientRepository;

    // find Doctor by Id
    public Optional<Doctors> findById(Long id) {
        Doctors doctor = entityManager.find(Doctors.class, id);

        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    // find all Doctors from DB
    public List<Doctors> findAllDoctorsDB() {
        return entityManager.createQuery("from Doctors").getResultList();
    }

    //find all Doctors currently in the web app
    public List<Doctors> findAll() {
        return entityManager
                .createQuery("from Doctors WHERE isDeleted = 0", Doctors.class)
                .getResultList();
    }


    public Optional<Doctors> findByEmailAndPass(String email, String password) throws WrongCredentials {
        try {
            Doctors doctor = entityManager.createQuery("from Doctors  WHERE email = :email " + "and password = :password", Doctors.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();

            return doctor != null ? Optional.of(doctor) : Optional.empty();
        } catch (Exception e) {
            throw wrong_credentials;
        }
    }

    // save a doctor
    public Optional<Doctors> save(Doctors doctor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctor);
            entityManager.getTransaction().commit();

            return Optional.of(doctor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // update a doctor
    public Optional<Doctors> update(Doctors doctor) {

        Doctors doctorIn = entityManager.find(Doctors.class, doctor.getId());
        if (doctor.getFirstName() != null)
            doctorIn.setFirstName(doctor.getFirstName());
        if (doctor.getLastName() != null)
            doctorIn.setLastName(doctor.getLastName());
        if (doctor.getEmail() != null)
            doctorIn.setEmail(doctor.getEmail());
        if (doctor.getLastActive() != null)
            doctorIn.setLastActive(doctor.getLastActive());
        if (doctor.getPassword() != null)
            doctorIn.setPassword(doctor.getPassword());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctorIn);
            entityManager.getTransaction().commit();

            return Optional.of(doctorIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // Get Doctor's Patients
    public List<Patients> myPatients(Long id) {
        return entityManager.createQuery("from Patients WHERE doctor_id = :id", Patients.class)
                .setParameter("id", id)
                .getResultList();
    }

    // Get available Patients
    public List<Patients> availablePatientsFromTo(Date from, Date to) {
        return entityManager.createQuery("from Patients patient WHERE patient.canBeExamined = true  " +
                "and patient.doctor_id = null" +
                "and patient.creationDate >= :fromDate and patient.creationDate <= :toDate", Patients.class)
                .setParameter("fromDate", from)
                .setParameter("toDate", to)
                .getResultList();
    }

    public Optional<Doctors> softDelete(Doctors d) {
        patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        Doctors doctorsIn = entityManager.find(Doctors.class, d.getId());
        for (Patients patient : doctorsIn.getPatients()) {
            patientRepository.removeDoctor(patient);
        }
        doctorsIn.setDeleted(true);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctorsIn);
            entityManager.getTransaction().commit();
            return Optional.of(doctorsIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Doctors> findInactiveDoctors() {
        List<Doctors> doctors = entityManager.createQuery("from Doctors WHERE isDeleted = 0").getResultList();
        List<Doctors> inactiveDoctors = new ArrayList<>();
        Date now = new Date();
        for (Doctors doctor : doctors) {
            long inactiveDays = TimeUnit.DAYS.convert(Math.abs(doctor.getLastActive().getTime() - now.getTime()), TimeUnit.MILLISECONDS);
            if (inactiveDays >= 15)
                inactiveDoctors.add(doctor);
        }

        return inactiveDoctors;
    }
}
