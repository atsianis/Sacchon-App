package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AllDoctorsDBImpl extends ServerResource implements AllDoctorsDB {
    public static final Logger LOGGER = Engine.getLogger(AllDoctorsDBImpl.class);
    private DoctorRepository doctorRepository;

    @Override
    protected void doInit() {
        LOGGER.info("Initialising doctor resource starts");
        try {
            doctorRepository = new DoctorRepository(JpaUtil.getEntityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising doctor resource ends");
    }

    @Override
    public ResponseRepresentation<List<DoctorRepresentation>> getAllDoctorsDB() {
        LOGGER.finer("Select all doctors.");
        try {
            List<Doctors> doctors = doctorRepository.findAllDoctorsDB();
            for (Doctors d : doctors)
                Hibernate.initialize(d.getConsultations());
            List<DoctorRepresentation> result = new ArrayList<>();
            doctors.forEach(doc -> result.add(new DoctorRepresentation(doc)));

            return new ResponseRepresentation<>(200, "Doctors retrieved", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Doctors not found", null);
        }
    }
}
