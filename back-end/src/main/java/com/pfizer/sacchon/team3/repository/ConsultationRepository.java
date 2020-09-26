package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.PatientRecords;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    public List<Consultations> findPatientsConsultations(long id){
        List<Consultations> consultations = entityManager
                .createQuery("SELECT from Consultations c WHERE c.patients_id = :patient_id ", Consultations.class)
                .setParameter("patient_id" , id)
                .getResultList();

        return consultations;
    }

    // save a doctor
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

        Query query = entityManager.createQuery("UPDATE Patients p SET p.canBeExamined = false "
                + "WHERE p.id = :patient_id")
                .setParameter("patient_id", c.getPatient().getId());


        return Optional.empty();
    }
}
