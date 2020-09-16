package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Doctor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class DoctorRepository {
    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Doctor> findById(Long id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    public List<Doctor> findAll() {
        return entityManager.createQuery("from Doctor").getResultList();
    }


    public Optional<Doctor> save(Doctor doctor){

        try {
            entityManager.getTransaction().begin();
            entityManager.persist (doctor);
            entityManager.getTransaction().commit();
            return Optional.of(doctor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Doctor> update(Doctor doctor) {

        Doctor inDBDoc = entityManager.find(Doctor.class, doctor.getId());
        inDBDoc.setFirstName(doctor.getFirstName());
        inDBDoc.setLastName(doctor.getLastName());
        inDBDoc.setEmail(doctor.getEmail());
        inDBDoc.setPassword(doctor.getPassword());
        inDBDoc.setLastActive(doctor.getLastActive());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (inDBDoc);
            entityManager.getTransaction().commit();
            return Optional.of(inDBDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
