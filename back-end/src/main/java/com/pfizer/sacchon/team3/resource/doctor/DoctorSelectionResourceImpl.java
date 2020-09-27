package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;
import java.util.logging.Logger;

public class DoctorSelectionResourceImpl extends ServerResource implements DoctorSelectionResource {

    public static final Logger LOGGER = Engine.getLogger(DoctorSelectionResourceImpl.class);
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private long patient_id;
    private long doctor_id;

    @Override
    protected void doInit() {
        LOGGER.info("Doctor's patients resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
            patientRepository= new PatientRepository(JpaUtil.getEntityManager());
            patient_id = Long.parseLong(getAttribute("pid"));
            doctor_id = Long.parseLong(getAttribute("did"));
        } catch (Exception ex) {
            throw new ResourceException(ex);
        }
        LOGGER.info("Doctor selects Patient resource ends");
    }

    @Override
    public boolean selectPatient() throws NotFoundException {
        LOGGER.finer("Doctor selects a patient starts");
        try{
            Doctors doctor;
            Patients patient;
            Optional<Doctors> odoctor = doctorRepository.findById(doctor_id);
            Optional<Patients> opatient = patientRepository.findById(patient_id);
            setExisting(odoctor.isPresent());
            if(isExisting()){
                doctor = odoctor.get();
            }else{
                throw new NotFoundException("Doctor not found");
            }
            setExisting(opatient.isPresent());
            if(isExisting()){
                patient = opatient.get();
            }else{
                throw new NotFoundException("Patient not found");
            }
            if (patient.getDoctor()==null){
                patient.setDoctor(doctor);
                try{
                    patientRepository.update(patient);
                    return true;
                }catch(Exception e){
                    throw new NotFoundException("Update could not be done");
                }
            }else{
                return false;
            }
        }catch(Exception e){
            throw new NotFoundException("Patient or Doctor dont exist");
        }
    }
}
