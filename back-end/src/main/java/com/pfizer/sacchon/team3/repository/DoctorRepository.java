package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;

import javax.persistence.EntityManager;
import java.util.*;

public class DoctorRepository {

    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private PatientRepository patientRepository;

    // find Doctor by Id
    public Optional<Doctors> findById(Long id) {
        Doctors doctor = entityManager.find(Doctors.class, id);

        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    // find all Doctors
    public List<Doctors> findAll() {
        return entityManager.createQuery("from Doctors").getResultList();
    }

    public Optional<Doctors> findByEmailAndPass(String email, String password) {
        Doctors doctor = entityManager.createQuery("from Doctors doctors WHERE doctor.email = email " + "and doctor.password = password", Doctors.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();

        return doctor != null ? Optional.of(doctor) : Optional.empty();
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
        doctorIn.setFirstName(doctor.getFirstName());
        doctorIn.setLastName(doctor.getLastName());
        doctorIn.setEmail(doctor.getEmail());
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

    // remove a doctor
    public boolean remove(Long id) {
        Optional<Doctors> opDoctor = findById(id);
        if (opDoctor.isPresent()) {
            Doctors doctor = opDoctor.get();
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(doctor);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    // Get Doctor's Patients
    public List<Patients> myPatients(Long id) {
        List<Patients> myPatients = entityManager.createQuery("from Patients p WHERE p.doctor_id = id")
                .setParameter("id", id)
                .getResultList();
        return myPatients;
    }

    // Get available Patients
    public List<Patients> availablePatientsFromTo(Date from, Date to) {
        List<Patients> availblePatients = entityManager.createQuery("from Patients patient WHERE patient.canBeExamined = true  " +
                "and patient.doctor_id = null" +
                "and patient.creationDate >= :fromDate and patient.creationDate <= :toDate")
                .setParameter("fromDate", from)
                .setParameter("toDate", to)
                .getResultList();
        return availblePatients;
    }

    public List<PatientRecords> patientRecords(Long id) {
        List<PatientRecords> patientsRecords = entityManager.createQuery("from PatientRecords patientRec WHERE patientRec.patient_id = id")
                .setParameter("id", id)
                .getResultList();
        return patientsRecords;
    }

    public Optional<Doctors> softDelete(Doctors d) {
        Doctors doctorsIn = entityManager.find(Doctors.class, d.getId());
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
}
