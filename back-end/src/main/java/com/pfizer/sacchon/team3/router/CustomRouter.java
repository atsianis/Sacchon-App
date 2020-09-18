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

        // εδω θα μπουνε τα endpoints

        router.attach("/patients", PatientListImpl.class);
        router.attach("/availablePatients", PatientListImpl.class);
        router.attach("/patient/{id}", PatientResourceImpl.class);
        router.attach("/patient/{id}/settings", PatientResourceImpl.class);
        router.attach("/patients/{id}/storeData", PatientRecordsListImpl.class);
        router.attach("/patients/{id}/storeData/allData", PatientRecordsListImpl.class);
        router.attach("/patient/{id}/storeData/patientRecord/{id}", PatientRecordResourceImpl.class);

        return router;
    }

    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
