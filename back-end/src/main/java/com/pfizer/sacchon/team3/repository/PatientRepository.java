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

    public List<Patients> findAllAvailablePatients() {
        List<Patients> patients = entityManager.createQuery("from Patients patient WHERE patient.canBeExamined = true  " +
                "and patient.doctor_id = null")
                .getResultList();
        return patients;
    }

    public Optional<Patients> save(Patients patients) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patients);
            entityManager.getTransaction().commit();
            return Optional.of(patients);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Patients> update(Patients p) {
        Patients patientIn = entityManager.find(Patients.class, p.getId());
        patientIn.setFirstName(p.getFirstName());
        patientIn.setLastName(p.getLastName());
        patientIn.setPassword(p.getPassword());
        patientIn.setEmail(p.getEmail());
        patientIn.setDob(p.getDob());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patientIn);
            entityManager.getTransaction().commit();
            return Optional.of(patientIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean remove(Long id) {
        Optional<Patients> patient = findById(id);
        if (patient.isPresent()) {
            Patients p = patient.get();
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(p);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public Optional<Patients> softDelete(Patients p) {
        Patients patientIn = entityManager.find(Patients.class, p.getId());
        patientIn.setDeleted(true);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patientIn);
            entityManager.getTransaction().commit();
            return Optional.of(patientIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
