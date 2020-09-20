package com.pfizer.sacchon.team3.repository;


import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.PatientRecords;
import com.pfizer.sacchon.team3.model.Patients;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DoctorRepository {

    private EntityManager entityManager;

    public DoctorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private PatientRepository patientRepository;

    // find Doctor by Id
    public Optional<Doctors> findById(Long id) {
        Doctors doctor = entityManager.find(Doctors.class, id);

        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    // find all Doctors
    public List<Doctors> findAll() {
        return entityManager.createQuery("from Doctor").getResultList();
    }


    // save a doctor
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

    // update a doctor
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

    // remove a doctor
    public boolean remove(Long id) {
        Optional<Doctors> opDoctor = findById(id);
        if (opDoctor.isPresent()) {
            Doctors doctor = opDoctor.get();
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(doctor);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    // Get Doctor's Patients
    public List<Patients> myPatients(Doctors d) {
        Doctors doctor = entityManager.find(Doctors.class, d.getId());
        List<Patients> allPatients = patientRepository.findAllPatients();
        List<Patients> myPatients = allPatients
                .stream()
                .filter(patient -> patient.getDoctor() == doctor)
                .collect(Collectors.toList());

        return myPatients;
    }

    // Get available Patients
    public List<Patients> availablePatients() {
        List<Patients> availablePatients = new ArrayList<>();
        List<Patients> allPatients = patientRepository.findAllPatients();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 30);

        List<PatientRecords> patientRecords = allPatients
                .stream()
                .flatMap(patient -> patient.getPatientRecords().stream())
                .filter(patient -> patient.getTimeCreated() == patient.getTimeCreated())
                .collect(Collectors.toList());

        for (PatientRecords pr : patientRecords)
            availablePatients.add(pr.getPatient());

        return availablePatients;
    }

    public List<PatientRecords> patientRecords(Patients p) {
        List<PatientRecords> patientRecord = new ArrayList<>();
        Patients patientsIn = entityManager.find(Patients.class, p.getId());
        patientRecord.addAll(patientsIn.getPatientRecords());

        return patientRecord;
    }

    public Optional<Doctors> softDelete(Doctors d) {
        Doctors doctorsIn = entityManager.find(Doctors.class, d.getId());
        doctorsIn.setDeleted(true);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctorsIn);
            entityManager.getTransaction().commit();
            return Optional.of(doctorsIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
