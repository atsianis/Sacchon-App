package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import com.pfizer.sacchon.team3.resource.doctor.DoctorResourceImpl;
import org.hibernate.Hibernate;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AllDoctorsListImpl extends ServerResource implements AllDoctorsList {
    public static final Logger LOGGER = Engine.getLogger(DoctorResourceImpl.class);
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
    public ResponseRepresentation<List<DoctorRepresentation>> getDoctors(){
        LOGGER.finer("Select all doctors.");
        try {
            List<Doctors> doctors = doctorRepository.findAll();
            for (Doctors d : doctors)
                Hibernate.initialize(d.getConsultations());
            List<DoctorRepresentation> result = new ArrayList<>();
            doctors.forEach(doc -> result.add(new DoctorRepresentation(doc)));

            return new ResponseRepresentation<List<DoctorRepresentation>>(200,"Doctors retrieved",result);
        } catch (Exception e) {
            return new ResponseRepresentation<List<DoctorRepresentation>>(404,"Doctors not found",null);
        }
    }
}
