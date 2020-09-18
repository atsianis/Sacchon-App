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

}
