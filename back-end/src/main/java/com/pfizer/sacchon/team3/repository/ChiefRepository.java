package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.exception.WrongCredentials;
import com.pfizer.sacchon.team3.model.Chiefs;

import javax.persistence.EntityManager;
import java.util.Optional;

public class ChiefRepository {
    private EntityManager entityManager;

    public ChiefRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param id
     * @return Optional of Chief
     *
     * Find chief doctor by his/her unique ID
     */
    public Optional<Chiefs> findById(Long id) {
        Chiefs chief = entityManager.find(Chiefs.class, id);

        return chief != null ? Optional.of(chief) : Optional.empty();
    }

    /**
     *
     * @param email
     * @param password
     * @return Optional of Chief
     * @throws WrongCredentials
     *
     * Find chief doctor by email and password.
     * Used for log in
     */
    public Optional<Chiefs> findByEmailAndPass(String email, String password) throws WrongCredentials {
        try {
            Chiefs chief = entityManager.createQuery("from Chiefs WHERE email = :email " + "and password = :password", Chiefs.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();

            return chief != null ? Optional.of(chief) : Optional.empty();
        } catch (Exception e) {
            throw new WrongCredentials("wrong credentials");
        }
    }

    /**
     *
     * @param chief
     * @return Optional of Chief
     *
     * Update chief doctor's personal data
     */
    public Optional<Chiefs> update(Chiefs chief) {
        Chiefs chiefs = entityManager.find(Chiefs.class, chief.getId());
        chiefs.setFirstName(chief.getFirstName());
        chiefs.setLastName(chief.getLastName());
        chiefs.setPassword(chief.getPassword());
        chiefs.setEmail(chief.getEmail());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(chiefs);
            entityManager.getTransaction().commit();
            return Optional.of(chiefs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
