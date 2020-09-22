package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Chiefs;

import javax.persistence.EntityManager;
import java.util.Optional;

public class ChiefRepository {
    private EntityManager entityManager;

    public ChiefRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Optional<Chiefs> findByEmailAndPass(String email, String password) {
        Chiefs chief = entityManager.createQuery("from Chiefs chief WHERE chief.email = email " + "and chief.password = password", Chiefs.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();

        return chief != null ? Optional.of(chief) : Optional.empty();
    }
}
