package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.exception.WrongCredentials;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PatientRepository {
    private EntityManager entityManager;

    public PatientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Patients> findById(Long id) {
        Patients patient = entityManager.find(Patients.class, id);

        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    // find all the patients from the Database
    public List<Patients> findAllPatientsDB() {
        return entityManager.createQuery("from Patients").getResultList();
    }

    public List<Patients> findAllPatients() {
        return entityManager
                .createQuery("from Patients WHERE isDeleted = 0", Patients.class)
                .getResultList();
    }

    /**
     * this method returns a List of Patients that have been recording for 30 days and await for a consultation
     * from a doctor . If they belong to a doctor a notification will appear on doctors dashboard .
     */
    public List<Patients> findAllConsultablePatients() {
        return entityManager.createQuery("from Patients WHERE canBeExamined = 1 ")
                .getResultList();
    }

    /**
     * this method returns a List of Patients that have registered to the system
     * and await for a doctor to select them .
     */
    public List<Patients> findAllAvailablePatients() {
        return entityManager.createQuery("from Patients WHERE doctor_id = null")
                .getResultList();
    }

    // find a patient by his mail and pass ** used for login
    public Optional<Patients> findByEmailAndPass(String email, String password) throws WrongCredentials {
        try {
            Patients patient = entityManager
                    .createQuery("from Patients WHERE email = :email " + "and password = :password", Patients.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();

            return patient != null ? Optional.of(patient) : Optional.empty();
        } catch (Exception e) {
            throw new WrongCredentials("wrong Credentials");
        }
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
        if (!(p.getFirstName() == null))
            patientIn.setFirstName(p.getFirstName());
        if (!(p.getLastName() == null))
            patientIn.setLastName(p.getLastName());
        //System.out.println(p.getPassword().equals(null));
        if (!(p.getPassword() == null))
            patientIn.setPassword(p.getPassword());
        if (!(p.getEmail() == null))
            patientIn.setEmail(p.getEmail());
        if (!(p.getDob() == null))
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

    public boolean removeDoctor(Patients patient) {
        Patients patientIn = entityManager.find(Patients.class, patient.getId());
        System.out.println(patientIn.getFirstName());
        if (patient.getDoctor() != null) {
            patientIn.setDoctor(null);
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patientIn);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Optional<Patients> softDelete(Patients p) {
        Patients patientIn = entityManager.find(Patients.class, p.getId());
        patientIn.setDeleted(true);
        patientIn.setDoctor(null);
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

    /*
     * this method doesnt allow a patient
     * to store Data after 30 days .
     * This happens because his doctor must leave
     * a consultation for these records .
     * This is implemented because of the 30 days request from the project requirements
     */
    public boolean checkLastConsultation(List<Consultations> consultations) {
        //sorts the consultation list in reverse order
        //element at index 0 is the most recent
        Collections.reverse(consultations);

        Consultations consultation = consultations.get(0);

        Date dateCreated = consultation.getTimeCreated();
        Date dateCurr = new Date();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(dateCreated); // Now use today date.
        c1.add(Calendar.DATE, 30); // Adding 30 days

        Calendar c2 = Calendar.getInstance();
        c2.setTime(dateCurr); // Now use today date.

        return c1.compareTo(c2) > 0; // canBeExamined = true notification
    }

    /*
     * this method doesnt allow a patient to record
     * data in a DateTime before the time he first
     * logged in the system .
     */
    public boolean checkPatientsCreationTime(PatientRecords patientRecord, Date patientsCreationDate) {
        return patientRecord.getTimeCreated().compareTo(patientsCreationDate) > 0;
    }

    /*
     * find all the patients who have been inactive
     *  over a specific time period
     */
    public List<Patients> findInactivePatients() {
        List<Patients> patients = entityManager.createQuery("from Patients WHERE isDeleted = 0", Patients.class).getResultList();
        List<Patients> inactivePatients = new ArrayList<>();
        Date now = new Date();
        for (Patients patient : patients) {
            long diff = TimeUnit.DAYS.convert(Math.abs(patient.getLastActive().getTime() - now.getTime()), TimeUnit.MILLISECONDS);
            if (diff >= 15) {
                inactivePatients.add(patient);
            }
        }

        return inactivePatients;
    }

    public boolean activeConsultationExists(List<Consultations> consultations) {
        Collections.reverse(consultations);
        Consultations consultation = consultations.get(0);

        return ( new Date().compareTo(consultation.getTimeCreated()) < 30 );
    }
}
