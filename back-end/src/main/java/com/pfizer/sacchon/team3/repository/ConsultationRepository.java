package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Consultations;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
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

    public List<Consultations> findPatientsConsultations(long patients_id){
        List<BigInteger> consultationsIDs = entityManager.createNativeQuery("select distinct pr.consultation_id from PatientRecords as pr \n" +
                "where pr.patient_id = "+patients_id+" ;").getResultList();

        List<Consultations> consultations = new ArrayList<>();
        consultationsIDs.forEach(c -> consultations.add(findById(c.longValue()).get()));
        consultations.forEach(c-> Hibernate.initialize(c.getPatientRecords()));

        return consultations;
    }
}
