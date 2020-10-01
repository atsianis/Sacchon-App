package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.PatientRecords;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PatientRecordRepository {

    private EntityManager entityManager;

    public PatientRecordRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param id
     * @return Optional of PatientRecord
     *
     * Find a record by its unique ID
     */
    public Optional<PatientRecords> findById(Long id) {
        PatientRecords p = entityManager.find(PatientRecords.class, id);

        return p != null ? Optional.of(p) : Optional.empty();
    }

    /**
     *
     * @return List of PatientRecords
     *
     * A list of all records in the Database
     */
    public List<PatientRecords> findAllPatientRecords() {
        return entityManager.createQuery("from PatientRecords").getResultList();
    }

    /**
     *
     * @param id
     * @return List of PatientRecord
     *
     * A list of all records that have been uploaded by a specific patient
     */
    public List<PatientRecords> findPatientRecordsByPatient(long id) {
        return entityManager.createQuery("from PatientRecords WHERE patient_id = :id")
                .setParameter("id", id)
                .getResultList();
    }

    /**
     *
     * @param patientRecord
     * @return Optional of PatientRecord
     *
     * Persist a record into the Database
     */
    public Optional<PatientRecords> save(PatientRecords patientRecord) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patientRecord);
            entityManager.getTransaction().commit();
            return Optional.of(patientRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     *
     * @param patientRecord
     * @return Optional of PatientRecord
     *
     * Update the measurements of a specific record.
     * Available for every patient, in case he/she uploaded wrong data
     */
    public Optional<PatientRecords> update(PatientRecords patientRecord) {
        PatientRecords patientRecordsIn = entityManager.find(PatientRecords.class, patientRecord.getId());
        patientRecordsIn.setGlycose(patientRecord.getGlycose());
        patientRecordsIn.setCarbs(patientRecord.getCarbs());
        patientRecordsIn.setTimeCreated(new Date());
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

    /**
     *
     * @param id
     * @return boolean
     *
     * Delete a record from the Database
     */
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
