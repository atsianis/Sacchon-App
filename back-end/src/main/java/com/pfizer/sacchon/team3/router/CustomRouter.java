package com.pfizer.sacchon.team3.router;

import com.pfizer.sacchon.team3.resource.chief.AllPatientsListImpl;
import com.pfizer.sacchon.team3.resource.chief.AllDoctorsListImpl;
import com.pfizer.sacchon.team3.resource.consultation.ConsultationListResourceImpl;
import com.pfizer.sacchon.team3.resource.consultation.ConsultationResourceImpl;
import com.pfizer.sacchon.team3.resource.doctor.AvailablePatientsFromToResourceImpl;
import com.pfizer.sacchon.team3.resource.doctor.DoctorResourceImpl;
import com.pfizer.sacchon.team3.resource.doctor.MyPatientsResourceImpl;
import com.pfizer.sacchon.team3.resource.PingServerResource;
import com.pfizer.sacchon.team3.resource.patient.PatientRecordsListImpl;
import com.pfizer.sacchon.team3.resource.patient.PatientResourceImpl;
import com.pfizer.sacchon.team3.resource.patientRecord.PatientRecordResourceImpl;
import org.restlet.Application;
import org.restlet.routing.Router;

public class CustomRouter {

    private Application application;

    public CustomRouter(Application application) {
        this.application = application;

    }

    public Router createApiRouter() {

        Router router = new Router(application.getContext());

        //router.attach("/patients", PatientListResourceImpl.class);

        router.attach("/patient/{id}", PatientResourceImpl.class);

        router.attach("/doctors", AllDoctorsListImpl.class);

        router.attach("/consultations", ConsultationListResourceImpl.class);

        router.attach("/consultation/{id}", ConsultationResourceImpl.class);

//        router.attach("/patients/inactive/{days}", PatientListResourceImp.class);
//
//        router.attach("/doctors/inactive/{days}", PingServerResource.class);

        router.attach("/create/doctor", DoctorResourceImpl.class);

        router.attach("/doctor/{id}", DoctorResourceImpl.class);
        // settings
        router.attach("/doctor/{id}/settings",  DoctorResourceImpl.class);

        // Doctor's patients
        router.attach("/doctor/{id}/mypatients",  MyPatientsResourceImpl.class);

        // Available patients
        router.attach("/doctor/available",  AvailablePatientsFromToResourceImpl.class);

        // Get Patients Consults
        router.attach("patient/{id}/consults",  AvailablePatientsFromToResourceImpl.class);

        // PUT DELETE Consultations
        router.attach("/ping", PingServerResource.class);
        router.attach("/patients", AllPatientsListImpl.class);
        router.attach("/availablePatients", AllPatientsListImpl.class);
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
