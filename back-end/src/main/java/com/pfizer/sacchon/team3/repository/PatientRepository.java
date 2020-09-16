package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Patients;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PatientRepository {
    private EntityManager entityManager;

    public PatientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Patients> findById(Long id) {
        Patients patients = entityManager.find(Patients.class, id);
        return patients != null ? Optional.of(patients) : Optional.empty();
    }

    public List<Patients> findAllPatients() {
        return entityManager.createQuery("from Patient").getResultList();
    }

    public Optional<Patients> findByLastName(String lastName) {
        Patients patients = entityManager.createQuery("SELECT b FROM Patient b WHERE b.lastName = :lastName", Patients.class)
                .setParameter("lastName", lastName)
                .getSingleResult();
        return patients != null ? Optional.of(patients) : Optional.empty();
    }

    public Optional<Patients> save(Patients patients){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (patients);
            entityManager.getTransaction().commit();
            return Optional.of(patients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<Patients> update(Patients patients) {
        Patients in = entityManager.find(Patients.class, patients.getId());
        in.setFirstName(patients.getFirstName());
        in.setLastName(patients.getLastName());
        in.setPassword(patients.getPassword());
        in.setEmail(patients.getEmail());
        in.setDob(patients.getDob());
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
        Optional<Patients> patient = findById(id);
        if (patient.isPresent()){
            Patients p = patient.get();
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
