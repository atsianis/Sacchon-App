package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PatientRecordRepository {

    private EntityManager entityManager;

    public PatientRecordRepository (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<PatientRecords> findById(Long id) {
        PatientRecords p = entityManager.find(PatientRecords.class, id);
        return p != null ? Optional.of(p) : Optional.empty();
    }

    public List<PatientRecords> findAllPatientRecords() {
        return entityManager.createQuery("from PatientRecords").getResultList();
    }

    /*public List<PatientRecords> findByPatient(Patients patient){
        PatientRecords p = entityManager.find(PatientRecords.class, );
        long pr = p.getPatients().getId()
    }*/

    public Optional<PatientRecords> save(PatientRecords p){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (p);
            entityManager.getTransaction().commit();
            return Optional.of(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Optional<PatientRecords> update(PatientRecords p) {
        PatientRecords in = entityManager.find(PatientRecords.class, p.getId());
        in.setSacchon(p.getSacchon());
        in.setCalories(p.getCalories());
        in.setTimeCreated(p.getTimeCreated());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist (in);
            entityManager.getTransaction().commit();
            return Optional.of(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean remove(Long id){
        Optional<PatientRecords> p = findById(id);
        if (p.isPresent()){
            PatientRecords patientRecords = p.get();
            try{
                entityManager.getTransaction().begin();
                entityManager.remove(p);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
