package com.pfizer.sacchon.team3.router;

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

        return router;
    }

}