package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Consultation;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ConsultationRepository {
    private EntityManager entityManager;

    public ConsultationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Consultation> findById(Long id) {
        Consultation consultation = entityManager.find(Consultation.class, id);
        return consultation != null ? Optional.of(consultation) : Optional.empty();
    }

    public List<Consultation> findAll() {
        return entityManager.createQuery("from Consultation").getResultList();
    }

}
