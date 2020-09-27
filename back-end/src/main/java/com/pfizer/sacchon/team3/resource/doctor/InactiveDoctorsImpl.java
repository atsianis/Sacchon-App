package com.pfizer.sacchon.team3.resource.doctor;

import com.pfizer.sacchon.team3.exception.NotFoundException;
import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.model.Patients;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.PatientRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.PatientRepresentation;
import com.pfizer.sacchon.team3.security.ResourceUtils;
import com.pfizer.sacchon.team3.security.Shield;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InactiveDoctorsImpl extends ServerResource implements InactiveDoctors{

    public static final Logger LOGGER = Engine.getLogger(InactiveDoctorsImpl.class);
    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising inactives doctors starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising inactives doctors resource ends");
    }

    @Override
    public List<DoctorRepresentation> inactiveDoctors() throws NotFoundException {
        LOGGER.finer("Select inactive doctors.");
        // Check authorization
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        try {
            List<Doctors> doctors = doctorRepository.findInactiveDoctors();
            List<DoctorRepresentation> result = new ArrayList<>();
            for (Doctors doctor : doctors) {
                Hibernate.initialize(doctor);
                result.add(new DoctorRepresentation(doctor));
            }
            return result;
        } catch (Exception e) {
            throw new NotFoundException("doctors not found");
        }
    }

}
