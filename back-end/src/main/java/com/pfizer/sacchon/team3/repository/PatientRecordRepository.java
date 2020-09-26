package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.PatientRecords;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PatientRecordRepository {

    private EntityManager entityManager;
    public PatientRecordRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<PatientRecords> findById(Long id) {
        PatientRecords p = entityManager.find(PatientRecords.class, id);

        return p != null ? Optional.of(p) : Optional.empty();
    }

    public List<PatientRecords> findAllPatientRecords() {
        return entityManager.createQuery("from PatientRecords").getResultList();
    }

    public Optional<PatientRecords> save(PatientRecords p) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(p);
            entityManager.getTransaction().commit();
            return Optional.of(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<PatientRecords> update(PatientRecords p) {
        PatientRecords patientRecordsIn = entityManager.find(PatientRecords.class, p.getId());
        patientRecordsIn.setSacchon(p.getSacchon());
        patientRecordsIn.setCalories(p.getCalories());
        patientRecordsIn.setTimeCreated(p.getTimeCreated());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patientRecordsIn);
            entityManager.getTransaction().commit();
            return Optional.of(patientRecordsIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean remove(Long id) {
        Optional<PatientRecords> OptPatientRecord = findById(id);
        if (OptPatientRecord.isPresent()) {
            PatientRecords patientRecord = OptPatientRecord.get();
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(patientRecord);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
