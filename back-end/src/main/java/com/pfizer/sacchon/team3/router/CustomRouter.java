package com.pfizer.sacchon.team3.router;

import com.pfizer.sacchon.team3.resource.*;
import org.restlet.Application;
import org.restlet.routing.Router;

public class CustomRouter {

    private Application application;

    public CustomRouter(Application application) {
        this.application = application;

    }

    public Router createApiRouter() {

        Router router = new Router(application.getContext());

        router.attach("/patients", PatientListResourceImp.class);

        router.attach("/patient/{id}", PatientResourceImpl.class);

        router.attach("/doctors", DoctorListResourceImpl.class);

        router.attach("/consultations", ConsultationListResourceImpl.class);

        router.attach("/consultation/{id}", ConsultationResourceImpl.class);

//        router.attach("/patients/inactive/{days}", PatientListResourceImp.class);
//
//        router.attach("/doctors/inactive/{days}", PingServerResource.class);

        router.attach("/create/doctor", DoctorResourceImpl.class);

        router.attach("/doctor/{id}", DoctorResourceImpl.class);

        return router;
    }

    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
