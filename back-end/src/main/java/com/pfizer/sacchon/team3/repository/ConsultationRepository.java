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

    /**
     *
     * @param id
     * @return Optional of Consultation
     *
     * Find a consultation by its unique ID
     */
    public Optional<Consultations> findById(Long id) {
        Consultations consultation = entityManager.find(Consultations.class, id);

        return consultation != null ? Optional.of(consultation) : Optional.empty();
    }

    /**
     *
     * @return List of Consultations
     *
     * A List of all Consultations persisted in the Database
     */
    public List<Consultations> findAll() {
        return entityManager.createQuery("from Consultations").getResultList();
    }

    /**
     *
     * @param id
     * @return List of Consultations
     *
     * Find the consultations of a specific patient
     */
    public List<Consultations> findPatientsConsultations(long id) {
        return entityManager
                .createQuery("from Consultations WHERE patient_id = :patient_id ", Consultations.class)
                .setParameter("patient_id", id)
                .getResultList();
    }

    /**
     *
     * @param id
     * @return List of Consultations
     *
     * Find the consultations of a specific doctor
     */
    public List<Consultations> findDoctorConsultations(long id) {
        return entityManager
                .createQuery("from Consultations WHERE doctor_id = :doctor_id ", Consultations.class)
                .setParameter("doctor_id", id)
                .getResultList();
    }

    /**
     *
     * @param consultation
     * @return Optional of Consultation
     *
     * Persist a Consultation into the Database
     */
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

    /**
     *
     * @param consultation
     * @return Optional of Consultation
     *
     * Update the comment of an existing consultation
     * Available for every doctor who wants to change an already saved consultation
     */
    public Optional<Consultations> setComment(Consultations consultation) {
        Consultations consultationsIn = entityManager.find(Consultations.class, consultation.getId());
        consultationsIn.setComment(consultation.getComment());
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
