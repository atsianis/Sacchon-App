package com.pfizer.sacchon.team3.resource.register;

import com.pfizer.sacchon.team3.exception.BadEntityException;
import com.pfizer.sacchon.team3.repository.DoctorRepository;
import com.pfizer.sacchon.team3.repository.util.JpaUtil;
import com.pfizer.sacchon.team3.representation.DoctorRepresentation;
import com.pfizer.sacchon.team3.resource.doctor.DoctorResourceImpl;
import org.restlet.engine.Engine;
import org.restlet.resource.ServerResource;

import java.util.logging.Logger;

public class RegisterDoctorImpl extends ServerResource implements RegisterDoctor {
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
    public DoctorRepresentation add(DoctorRepresentation companyReprIn) throws BadEntityException {
        return null;
    }
}
