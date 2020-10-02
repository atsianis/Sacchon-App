package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.exception.WrongCredentials;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DoctorRepository {

    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private PatientRepository patientRepository;

    /**
     * @param id
     * @return Optional of Doctor
     * <p>
     * Find a doctor by his unique ID
     */
    public Optional<Doctors> findById(Long id) {
        Doctors doctor = entityManager.find(Doctors.class, id);

        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    /**
     * @return List of Doctors
     * <p>
     * A List of all doctors int he database, regardless of their state
     * Available for the chief doctor
     */
    public List<Doctors> findAllDoctorsDB() {
        return entityManager.createQuery("from Doctors").getResultList();
    }

    /**
     * @return List of Doctors
     * <p>
     * A List of all non-deleted doctors
     */
    public List<Doctors> findAll() {
        return entityManager
                .createQuery("from Doctors WHERE isDeleted = 0", Doctors.class)
                .getResultList();
    }

    /**
     * @param email
     * @param password
     * @return Optional of Doctor
     * @throws WrongCredentials Find Doctor by his/her credentials
     *                          Used for log in
     */
    public Optional<Doctors> findByEmailAndPass(String email, String password) throws WrongCredentials {
        try {
            Doctors doctor = entityManager.createQuery("from Doctors  WHERE email = :email " + "and password = :password", Doctors.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();

            return doctor != null ? Optional.of(doctor) : Optional.empty();
        } catch (Exception e) {
            throw new WrongCredentials("wrong credentials");
        }
    }

    /**
     * @param doctor
     * @return Optional of Doctor
     * <p>
     * Persist a Doctor into the database
     */
    public Optional<Doctors> save(Doctors doctor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctor);
            entityManager.getTransaction().commit();

            return Optional.of(doctor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * @param doctor
     * @return Optional of Doctor
     * <p>
     * Update doctor's personal data
     */
    public Optional<Doctors> update(Doctors doctor) {
        Doctors doctorIn = entityManager.find(Doctors.class, doctor.getId());
        doctorIn.setFirstName(doctor.getFirstName());
        doctorIn.setLastName(doctor.getLastName());
        doctorIn.setEmail(doctor.getEmail());
        doctorIn.setPassword(doctor.getPassword());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctorIn);
            entityManager.getTransaction().commit();

            return Optional.of(doctorIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * @param id
     * @return List of Patients
     * <p>
     * List of all patients undertaken by a specific doctor
     */
    public List<Patients> myPatients(Long id) {
        return entityManager.createQuery("from Patients WHERE doctor_id = :id", Patients.class)
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * @param doctor
     * @return Optional of Doctor
     * <p>
     * Softly delete a Doctor
     * His record-consultation history and personal data are still
     * kept in the Database and are available for the chief doctor
     */
    public Optional<Doctors> softDelete(Doctors doctor) {
        patientRepository = new PatientRepository(JpaUtil.getEntityManager());
        Doctors doctorIn = entityManager.find(Doctors.class, doctor.getId());
        for (Patients patient : doctorIn.getPatients()) {
            patientRepository.removeDoctor(patient);
        }
        doctorIn.setDeleted(true);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctorIn);
            entityManager.getTransaction().commit();
            return Optional.of(doctorIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * @return List of Doctors
     * <p>
     * List of Doctors who haven't submitted a consultation in the last 15 or more days
     */
    public List<Doctors> findInactiveDoctors() {
        List<Doctors> doctors = entityManager.createQuery("from Doctors WHERE isDeleted = 0").getResultList();
        List<Doctors> inactiveDoctors = new ArrayList<>();
        Date now = new Date();
        for (Doctors doctor : doctors) {
            long inactiveDays = TimeUnit.DAYS.convert(Math.abs(doctor.getLastActive().getTime() - now.getTime()), TimeUnit.MILLISECONDS);
            if (inactiveDays >= 15)
                inactiveDoctors.add(doctor);
        }

        return inactiveDoctors;
    }
}
