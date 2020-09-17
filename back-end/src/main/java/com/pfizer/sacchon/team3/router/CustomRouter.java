package com.pfizer.sacchon.team3.router;

import com.pfizer.sacchon.team3.resource.PatientListImpl;
import com.pfizer.sacchon.team3.resource.PatientResourceImpl;
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

        // εδω θα μπουνε τα endpoints

        router.attach("/patients", PatientListImpl.class);
        router.attach("/alailablePatients", PatientListImpl.class);
        router.attach("/patient/{id}", PatientResourceImpl.class);
        router.attach("/patient/{id}/settings", PatientResourceImpl.class);
        router.attach("/patients/{id}/storeData", PatientResourceImpl.class);

        return router;
    }

    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
