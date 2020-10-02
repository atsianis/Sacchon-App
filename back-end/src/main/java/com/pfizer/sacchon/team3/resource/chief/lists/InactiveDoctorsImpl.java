package com.pfizer.sacchon.team3.resource.chief.lists;

import com.pfizer.sacchon.team3.model.Doctors;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.representation.ResponseRepresentation;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InactiveDoctorsImpl extends ServerResource implements InactiveDoctors {

    public static final Logger LOGGER = Engine.getLogger(InactiveDoctorsImpl.class);
    private DoctorRepository doctorRepository;
    private EntityManager em = JpaUtil.getEntityManager();

    @Override
    protected void doRelease() {
        em.close();
    }

    @Override
    protected void doInit() {
        LOGGER.info("Initialising inactives doctors starts");
        try {
            doctorRepository = new DoctorRepository(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Initialising inactives doctors resource ends");
    }

    @Override
    public ResponseRepresentation<List<DoctorRepresentation>> inactiveDoctors() {
        LOGGER.finer("Select inactive doctors.");
        try {
            List<Doctors> doctors = doctorRepository.findInactiveDoctors();
            List<DoctorRepresentation> result = new ArrayList<>();
            for (Doctors doctor : doctors)
                result.add(new DoctorRepresentation(doctor));

            return new ResponseRepresentation<>(200, "Doctors retrieved", result);
        } catch (Exception e) {
            return new ResponseRepresentation<>(404, "Not found", null);
        }
    }

}
