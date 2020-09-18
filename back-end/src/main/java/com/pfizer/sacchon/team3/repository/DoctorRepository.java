package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Doctors;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class DoctorRepository {
    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Doctors> findById(Long id) {
        Doctors doctor = entityManager.find(Doctors.class, id);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    public List<Doctors> findAll() {
        return entityManager.createQuery("from Doctors").getResultList();
    }


    public Optional<Doctors> save(Doctors doctor){

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

    public Optional<Doctors> update(Doctors doctor) {

        Doctors inDBDoc = entityManager.find(Doctors.class, doctor.getId());
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
