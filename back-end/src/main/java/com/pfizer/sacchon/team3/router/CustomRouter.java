package com.pfizer.sacchon.team3.router;

import com.pfizer.sacchon.team3.resource.chief.lists.*;
import com.pfizer.sacchon.team3.resource.chief.settings.ChiefResourceImpl;
import com.pfizer.sacchon.team3.resource.consultation.ConsultationResourceImpl;
import com.pfizer.sacchon.team3.resource.consultation.UpdateConsultationResource;
import com.pfizer.sacchon.team3.resource.doctor.*;
import com.pfizer.sacchon.team3.resource.patient.PatientConsultationsResourceImpl;
import com.pfizer.sacchon.team3.resource.patient.PatientRecordsListImpl;
import com.pfizer.sacchon.team3.resource.patient.PatientResourceImpl;
import com.pfizer.sacchon.team3.resource.patientRecord.PatientRecordResourceImpl;
import com.pfizer.sacchon.team3.resource.softDeletes.SDDoctorImpl;
import com.pfizer.sacchon.team3.resource.softDeletes.SDPatientImpl;
import com.pfizer.sacchon.team3.resource.userAuth.login.LoginChiefImpl;
import com.pfizer.sacchon.team3.resource.userAuth.login.LoginDoctorImpl;
import com.pfizer.sacchon.team3.resource.userAuth.login.LoginPatientImpl;
import com.pfizer.sacchon.team3.resource.userAuth.register.RegisterPatientImpl;
import com.pfizer.sacchon.team3.resource.userAuth.register.RegisterDoctorImpl;
import org.restlet.Application;
import org.restlet.routing.Router;

public class CustomRouter {

    private Application application;

    public CustomRouter(Application application) {
        this.application = application;
    }

    public Router createApiRouter() {

        Router router = new Router(application.getContext());

        //Endpoints for login
        router.attach("/login/chief", LoginChiefImpl.class);
        router.attach("/login/doctor", LoginDoctorImpl.class);
        router.attach("/login/patient", LoginPatientImpl.class);
        //Register Endpoints
        router.attach("/register/patient", RegisterPatientImpl.class);
        router.attach("/chief/register/doctor", RegisterDoctorImpl.class);
        //chief Endpoints
        router.attach("/chief/allpatientsDB", AllPatientsDBImpl.class);
        router.attach("/chief/alldoctorsDB", AllDoctorsDBImpl.class);
        router.attach("/chief/allpatients", AllPatientsListImpl.class);
        router.attach("/chief/alldoctors", AllDoctorsListImpl.class);
        router.attach("/chief/inactivedoctors", InactiveDoctorsImpl.class);
        router.attach("/chief/inactivepatients", InactivePatientsImpl.class);
        router.attach("/chief/allconsultations", ConsultationListResourceImpl.class);
        router.attach("/chief/settings/update", ChiefResourceImpl.class);
        //patient Endpoints
        router.attach("/patient/{id}", PatientResourceImpl.class);
        router.attach("/patient/{id}/settings", PatientResourceImpl.class);
        router.attach("/patient/{id}/storeData", PatientRecordsListImpl.class);
        router.attach("/patient/{id}/storeData/allData", PatientRecordsListImpl.class);
        router.attach("/patient/{pid}/storeData/patientRecord/{rid}", PatientRecordResourceImpl.class);
        router.attach("/patient/{id}/consultations", PatientConsultationsResourceImpl.class);
        //doctor Endpoints
        router.attach("/doctor/{id}", DoctorResourceImpl.class);
        router.attach("/doctor/{id}/settings",  DoctorResourceImpl.class);
        router.attach("/doctor/{id}/mypatients",  MyPatientsResourceImpl.class);
        router.attach("/doctor/{id}/available-patients",  AllAvailablePatientListResourceImpl.class);
        router.attach("/doctor/{did}/select/patient/{pid}", DoctorSelectionResourceImpl.class);
        router.attach("/doctor/{did}/consultation/{cid}", UpdateConsultationResource.class);
        router.attach("/doctor/{id}/consultable-patients",  AllConsultablePatientListResourceImpl.class);
        //soft Deletes
        router.attach("/patient/{id}/settings/softDelete", SDPatientImpl.class);
        router.attach("/doctor/{id}/settings/softDelete", SDDoctorImpl.class);

        // wtf ?
        router.attach("/consultation/{id}", ConsultationResourceImpl.class);

        return router;
    }
}
