package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.exception.WrongCredentials;
import com.pfizer.sacchon.team3.model.Consultations;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PatientRepository {
    private EntityManager entityManager;

    public PatientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * @param id
     * @return Optional of Patient
     * <p>
     * Find a Patient by his unique ID
     */
    public Optional<Patients> findById(Long id) {
        Patients patient = entityManager.find(Patients.class, id);

        return patient != null ? Optional.of(patient) : Optional.empty();
    }

    /**
     * @return List of Patients
     * <p>
     * A List of all the patients in the Database,
     * regardless of their state
     * Available to the chief doctor only
     */
    public List<Patients> findAllPatientsDB() {
        return entityManager.createQuery("from Patients").getResultList();
    }

    /**
     * @return List of Patients
     * <p>
     * A List of all non-deleted Patients
     */
    public List<Patients> findAllPatients() {
        return entityManager
                .createQuery("from Patients WHERE isDeleted = 0", Patients.class)
                .getResultList();
    }

    /**
     * A List of Patients that have been recording for 30 days and await for a consultation
     * from a doctor. If they belong to a doctor a notification will appear on doctors dashboard .
     */
    public List<Patients> findAllConsultablePatients() {
        return entityManager.createQuery("from Patients WHERE canBeExamined = 1 ")
                .getResultList();
    }

    /**
     * A List of Patients undertaken by a specific doctor
     * that have been recording for 30 days and await for a consultation.
     */
    public List<Patients> findConsultablePatientsByDoctor(long doctor_id) {
        return entityManager.createQuery("from Patients WHERE canBeExamined = 1 and doctor_id = :doctor_id ")
                .setParameter("doctor_id", doctor_id)
                .getResultList();
    }

    /**
     * A List of Patients that have registered to the system
     * and await for a doctor to select them .
     */
    public List<Patients> findAllAvailablePatients() {
        return entityManager.createQuery("from Patients WHERE doctor_id = null")
                .getResultList();
    }

    /**
     * @param email
     * @param password
     * @return Optional of Patient
     * @throws WrongCredentials Find a patient by his email and password.
     *                          Used for login
     */
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

    /**
     * @param patient
     * @return Optional of Patient
     * <p>
     * Persist a Patient into the database
     */
    public Optional<Patients> save(Patients patient) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patient);
            entityManager.getTransaction().commit();
            return Optional.of(patient);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * @param patient
     * @return Update patient's personal data
     */
    public Optional<Patients> update(Patients patient) {
        Patients patientIn = entityManager.find(Patients.class, patient.getId());
        patientIn.setFirstName(patient.getFirstName());
        patientIn.setLastName(patient.getLastName());
        patientIn.setPassword(patient.getPassword());
        patientIn.setEmail(patient.getEmail());
        patientIn.setDob(patient.getDob());
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

    /**
     * @param patient
     * @return boolean
     * <p>
     * In case of doctor soft delete, removes the relationship
     * and makes the patient available for other doctor
     */
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

    /**
     * @param patient
     * @return Optional of Patient
     * <p>
     * Make the patient unreachable from the application ui
     * His record-consultation history and personal data are still
     * kept in the Database
     */
    public Optional<Patients> softDelete(Patients patient) {
        Patients patientIn = entityManager.find(Patients.class, patient.getId());
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

    /**
     * @param patientRecord
     * @param patientsCreationDate
     * @return boolean
     * <p>
     * Make sure that under no circumstances a record with a date older
     * than the patient's register date is persisted in the Database
     */
    public boolean checkPatientsCreationTime(PatientRecords patientRecord, Date patientsCreationDate) {
        return patientRecord.getTimeCreated().compareTo(patientsCreationDate) > 0;
    }

    /**
     * @return List of Patients
     * <p>
     * Find the patients that have not logged into the system for 15 or more days
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

    /**
     * @param consultations
     * @return long
     * <p>
     * returns the days passed from the last consultation a patient had
     */
    public long lastConsultationInDays(List<Consultations> consultations) {
        Collections.reverse(consultations);
        Consultations consultation = consultations.get(0);

        return TimeUnit.DAYS.convert(Math.abs(consultation.getTimeCreated().getTime() - new Date().getTime()), TimeUnit.MILLISECONDS);
    }

    /**
     * @param patient
     * @throws SQLException Updates the canBeExamined field of a Patient.
     *                      Called at 2 occurrences:
     *                      * A patient inserts a record on the 30th day after his last consultation ( canBeExamined set from 0 to 1 )
     *                      * A doctor uploads a consultation for the patient ( canBeExamined set from 1 to 0 )
     */
    public void updateCanBeExamined(Patients patient) throws SQLException {
        Patients patientIn = entityManager.find(Patients.class, patient.getId());
        patientIn.setCanBeExamined(!patientIn.isCanBeExamined());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patientIn);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new SQLException();
        }
    }
}
