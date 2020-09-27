package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PatientRepository {
    private EntityManager entityManager;

    public PatientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Patients> findById(Long id) {
        Patients patient = entityManager.find(Patients.class, id);

        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public List<Patients> findAllPatients() {
        return entityManager.createQuery("from Patients").getResultList();
    }

    public List<Patients> findAllConsultablePatients() {
        List<Patients> patients = entityManager.createQuery("from Patients WHERE canBeExamined = 1 " +
                "and doctor_id = null")
                .getResultList();

        return patients;
    }

    public List<Patients> findAllAvailablePatients() {
        List<Patients> patients = entityManager.createQuery("from Patients WHERE canBeExamined = 0 " +
                "and doctor_id = null")
                .getResultList();

        return patients;
    }

    public Optional<Patients> findByEmailAndPass(String email, String password) {
        Patients patient = entityManager
                .createQuery("from Patients patient WHERE patient.email = email " + "and patient.password = password", Patients.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();

        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    public Optional<Patients> save(Patients patients) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patients);
            entityManager.getTransaction().commit();
            return Optional.of(patients);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Patients> update(Patients p) {
        Patients patientIn = entityManager.find(Patients.class, p.getId());
        patientIn.setFirstName(p.getFirstName());
        patientIn.setLastName(p.getLastName());
        patientIn.setPassword(p.getPassword());
        patientIn.setEmail(p.getEmail());
        patientIn.setDob(p.getDob());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patientIn);
            entityManager.getTransaction().commit();
            return Optional.of(patientIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean remove(Long id) {
        Optional<Patients> patient = findById(id);
        if (patient.isPresent()) {
            Patients p = patient.get();
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(p);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public Optional<Patients> softDelete(Patients p) {
        Patients patientIn = entityManager.find(Patients.class, p.getId());
        patientIn.setDeleted(true);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patientIn);
            entityManager.getTransaction().commit();
            return Optional.of(patientIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean checkLastConsultation(PatientRecords patientRecord,List<Consultations> consultations) {
        //sorts the consultation list in reverse order
        //element at index 0 is the most recent
        Collections.reverse(consultations);

        Consultations consultation = consultations.get(0);

        Date dateCreated = consultation.getTimeCreated();
        Date dateCurr = patientRecord.getTimeCreated();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(dateCreated); // Now use today date.
        c1.add(Calendar.DATE, 30); // Adding 30 days

        Calendar c2 = Calendar.getInstance();
        c2.setTime(dateCurr); // Now use today date.

        return c1.compareTo(c2) > 0 // canBeExamined = true notification
    }

    public boolean checkPatientsCreationTime(PatientRecords patientRecord,Date patientsCreationDate){
        return patientRecord.getTimeCreated().compareTo(patientsCreationDate) < 0
    }
}
