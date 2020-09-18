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
        Patients patient = entityManager.find(Patients.class, id);
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public List<Patients> findAllPatients() {
        return entityManager.createQuery("from Patients").getResultList();
    }

    public Optional<Patients> findByLastName(String lastName) {
        Patients patients = entityManager.createQuery("SELECT b FROM Patients b WHERE b.lastName = :lastName", Patients.class)
                .setParameter("lastName", lastName)
                .getSingleResult();
        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public List<Patients> findAllAvailablePatients(boolean canBeExamined) {
        List<Patients> patients = entityManager.createQuery("SELECT b FROM Patients b WHERE b.canBeExamined = true", Patients.class)
                .setParameter("canBeExamined", canBeExamined)
                .getResultList();
        return patients;
    }

    public Optional<Patients> save(Patients patients){
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


    public Optional<Patients> update(Patients patient) {
        Patients in = entityManager.find(Patients.class, patient.getId());
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

    public Optional<Patients> softDelete(Patients patients) {
        Patients in = entityManager.find(Patients.class, patients.getId());
        in.setDeleted(true);
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
}
