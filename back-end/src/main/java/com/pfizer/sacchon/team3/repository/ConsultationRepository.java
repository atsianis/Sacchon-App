package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Consultations;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ConsultationRepository {
    private EntityManager entityManager;

    public ConsultationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Consultations> findById(Long id) {
        Consultations consultation = entityManager.find(Consultations.class, id);

        return consultation != null ? Optional.of(consultation) : Optional.empty();
    }

    public List<Consultations> findAll() {
        return entityManager.createQuery("from Consultations").getResultList();
    }

    public List<Consultations> findPatientsConsultations(long id) {
        return entityManager
                .createQuery("from Consultations WHERE patient_id = :patient_id ", Consultations.class)
                .setParameter("patient_id", id)
                .getResultList();
    }

    public List<Consultations> findDoctorConsultations(long id) {
        return entityManager
                .createQuery("from Consultations WHERE doctor_id = :doctor_id ", Consultations.class)
                .setParameter("doctor_id", id)
                .getResultList();
    }

    // save a consultation
    public Optional<Consultations> save(Consultations consultation) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(consultation);
            entityManager.getTransaction().commit();

            return Optional.of(consultation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Consultations> addNewComment(Consultations c) {
        Consultations consultationsIn = entityManager.find(Consultations.class, c.getId());
        consultationsIn.setComment(c.getComment());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(consultationsIn);
            entityManager.getTransaction().commit();
            return Optional.of(consultationsIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        entityManager.createQuery("UPDATE Patients p SET p.canBeExamined = false "
                + "WHERE p.id = :patient_id")
                .setParameter("patient_id", c.getPatient().getId());

        return Optional.empty();
    }

    public Optional<Consultations> setComment(Consultations c) {
        Consultations consultationsIn = entityManager.find(Consultations.class, c.getId());
        consultationsIn.setComment(c.getComment());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(consultationsIn);
            entityManager.getTransaction().commit();
            return Optional.of(consultationsIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
