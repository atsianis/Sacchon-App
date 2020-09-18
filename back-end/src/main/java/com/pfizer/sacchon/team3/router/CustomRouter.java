package com.pfizer.sacchon.team3.router;

import com.pfizer.sacchon.team3.resource.DoctorRes.AvailablePatients;
import com.pfizer.sacchon.team3.resource.DoctorRes.AvailablePatientsResourceImpl;
import com.pfizer.sacchon.team3.resource.DoctorRes.DoctorResourceImpl;
import com.pfizer.sacchon.team3.resource.DoctorRes.MyPatientsResourceImpl;
import com.pfizer.sacchon.team3.resource.PingServerResource;
import org.restlet.Application;
import org.restlet.routing.Router;

public class CustomRouter {

    private Application application;

    public CustomRouter(Application application) {
        this.application = application;

    }

    public Router createApiRouter() {

        Router router = new Router(application.getContext());

        // settings
        router.attach("/doctor/{id}/settings",  DoctorResourceImpl.class);

        // Doctor's patients
        router.attach("/doctor/{id}/mypatients",  MyPatientsResourceImpl.class);

        // Available patients
        router.attach("/doctor/available",  AvailablePatientsResourceImpl.class);

        // Get Patients Consults
        router.attach("patient/{id}/consults",  AvailablePatientsResourceImpl.class);

        // PUT DELETE Consultations

        return router;
    }

    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }

}
