package com.pfizer.sacchon.team3.router;

import com.pfizer.sacchon.team3.resource.DoctorResourceImpl;
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

        // endpoints
        router.attach("/doctor/{id}",  DoctorResourceImpl.class);
        router.attach("/doctor",  DoctorResourceImpl.class);
        router.attach("/doctor/",  DoctorResourceImpl.class);

        return router;
    }

    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }

}
