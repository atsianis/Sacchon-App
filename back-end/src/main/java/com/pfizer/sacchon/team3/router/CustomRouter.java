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

        router.attach("/reporter/patients", PatientListResourceImp.class);

        router.attach("/reporter/patient/{id}", PatientResourceImpl.class);

        router.attach("/reporter/doctors", DoctorListResourceImpl.class);

        router.attach("/reporter/consultations", ConsultationListResourceImpl.class);

        router.attach("/reporter/patients/inactive/{days}", PatientListResourceImp.class);

        router.attach("/reporter/doctors/inactive/{days}", PingServerResource.class);

        router.attach("/reporter/create/doctor", DoctorResourceImpl.class);

        router.attach("/doctor/{id}", DoctorResourceImpl.class);

        router.attach("/doctor/{id}/consultations", DoctorResourceImpl.class);

        

        return router;
    }

    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
