package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Patient;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PatientRepository {
    private EntityManager entityManager;

    public PatientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Patient> findById(Long id) {
        Patient patient = entityManager.find(Patient.class, id);
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public List<Patient> findAllPatients() {
        return entityManager.createQuery("from Patient").getResultList();
    }

    public Optional<Patient> findByLastName(String lastName) {
        Patient patient = entityManager.createQuery("SELECT b FROM Patient b WHERE b.lastName = :lastName", Patient.class)
                .setParameter("lastName", lastName)
                .getSingleResult();
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public Optional<Patient> save(Patient patient){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (patient);
            entityManager.getTransaction().commit();
            return Optional.of(patient);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<Patient> update(Patient patient) {
        Patient in = entityManager.find(Patient.class, patient.getId());
        in.setFirstName(patient.getFirstName());
        in.setLastName(patient.getLastName());
        in.setPassword(patient.getPassword());
        in.setEmail(patient.getEmail());
        in.setDob(patient.getDob());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (in);
            entityManager.getTransaction().commit();
            return Optional.of(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean remove(Long id){
        Optional<Patient> patient = findById(id);
        if (patient.isPresent()){
            Patient p = patient.get();
            try{
                entityManager.getTransaction().begin();
                entityManager.remove(p);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
