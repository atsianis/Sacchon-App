package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Chiefs;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ChiefRepository {
    private EntityManager entityManager;

    public ChiefRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Optional<Chiefs> findByEmailAndPass(String email, String password) {
        Chiefs chief = entityManager.createQuery("SELECT ca from Chiefs c WHERE c.email = :email " + "and c.password = :password", Chiefs.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();

        return chief != null ? Optional.of(chief) : Optional.empty();
    }

    public Optional<Chiefs> findByEmail(String email){
        Chiefs chief = entityManager.createQuery("from Chiefs WHERE email = :email", Chiefs.class)
                .setParameter("email" , email)
                .getSingleResult();
        return chief != null ? Optional.of(chief) : Optional.empty();
    }
}
