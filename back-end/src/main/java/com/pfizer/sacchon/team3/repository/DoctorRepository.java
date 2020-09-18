package com.pfizer.sacchon.team3.repository;

import com.pfizer.sacchon.team3.model.Doctor;
import com.pfizer.sacchon.team3.model.Patient;
import com.pfizer.sacchon.team3.model.PatientRecord;

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
    private PatientRepository patientRepository ;

    // find Doctor by Name
    public Optional<Doctor> findById(Long id) {
        Doctor doctor = entityManager.find(Doctor.class, id);
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    // find all Doctors
    public List<Doctor> findAll() {
        return entityManager.createQuery("from Doctor").getResultList();
    }

    // find Doctor by FirstName 1st way
    public Optional<Doctor> findByName(String name) {
        Doctor doctor = entityManager.createQuery("SELECT b FROM Doctor b WHERE b.firstname = :firstName", Doctor.class)
                .setParameter("firstName", name)
                .getSingleResult();
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    // find Doctor by FirstName 2nd way
    public Optional<Doctor> findByNameNamedQuery(String name) {
        Doctor doctor = entityManager.createNamedQuery("Doctor.findByName", Doctor.class)
                .setParameter("firstname", name)
                .getSingleResult();
        return doctor != null ? Optional.of(doctor) : Optional.empty();
    }

    // save a doctor
    public Optional<Doctor> save(Doctor doctor){

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
    public Optional<Doctor> update(Doctor doctor) {

        Doctor in = entityManager.find(Doctor.class, doctor.getId());
        in.setFirstName(doctor.getFirstName());
        in.setLastName(doctor.getLastName());
        in.setEmail(doctor.getEmail());
        in.setPassword(doctor.getPassword());
        in.setLastActive(doctor.getLastActive());

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(in);
            entityManager.getTransaction().commit();
            return Optional.of(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // remove a doctor
    public boolean remove(Long id){
        Optional<Doctor> odoctor = findById(id);
        if (odoctor.isPresent()){
            Doctor p = odoctor.get();
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

    // Get Doctor's Patients
    public List<Patient> myPatients(Doctor doctor){
        Doctor in = entityManager.find(Doctor.class, doctor.getId());
        List<Patient> myPatients = new ArrayList<>();
        List<Patient> allPatients = patientRepository.findAllPatients();

        myPatients = allPatients.stream()
                                .filter(patient -> patient.getDoctor() == in)
                                .collect(Collectors.toList());

        return myPatients;
    }

    // Get available Patients
    public List<Patient> availablePatients(){
        List<Patient> avPatients = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        List<Patient> patients = patientRepository.findAllPatients();

        cal.add(Calendar.DAY_OF_MONTH, 30);
        //String newDate = sdf.format(cal.getTime());

        List<PatientRecord> patientRecords = patients
                                            .stream()
                                            .flatMap(patient -> patient.getPatientRecords().stream())
                                            .filter(patient -> patient.getTimeCreated() == patient.getTimeCreated())
                                            .collect(Collectors.toList());

        for(PatientRecord pacRec: patientRecords)
            avPatients.add(pacRec.getPatient());

        return avPatients;
    }

    public List<PatientRecord> patientRecords(Patient patient) {
        List<PatientRecord> patientRecord = new ArrayList<>();
        Patient in = entityManager.find(Patient.class, patient.getId());
        patientRecord.addAll(in.getPatientRecords());

        return patientRecord;
    }

}
