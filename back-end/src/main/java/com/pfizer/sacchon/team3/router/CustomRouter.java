package com.pfizer.sacchon.team3.router;

import com.pfizer.sacchon.team3.resource.chief.lists.*;
import com.pfizer.sacchon.team3.resource.chief.settings.ChiefResourceImpl;
import com.pfizer.sacchon.team3.resource.consultation.AddConsultationResourceImpl;
import com.pfizer.sacchon.team3.resource.consultation.ConsultationResourceImpl;
import com.pfizer.sacchon.team3.resource.consultation.UpdateConsultationResource;
import com.pfizer.sacchon.team3.resource.doctor.*;
import com.pfizer.sacchon.team3.resource.patient.PatientConsultationsResourceImpl;
import com.pfizer.sacchon.team3.resource.patient.PatientRecordsListImpl;
import com.pfizer.sacchon.team3.resource.patient.PatientResourceImpl;
import com.pfizer.sacchon.team3.resource.patientRecord.PatientRecordResourceImpl;
import com.pfizer.sacchon.team3.resource.softDeletes.SoftDeleteDoctorImpl;
import com.pfizer.sacchon.team3.resource.softDeletes.SoftDeletePatientImpl;
import com.pfizer.sacchon.team3.resource.userAuth.login.LoginChiefImpl;
import com.pfizer.sacchon.team3.resource.userAuth.login.LoginDoctorImpl;
import com.pfizer.sacchon.team3.resource.userAuth.login.LoginPatientImpl;
import com.pfizer.sacchon.team3.resource.userAuth.register.RegisterDoctorImpl;
import com.pfizer.sacchon.team3.resource.userAuth.register.RegisterPatientImpl;
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
        router.attach("/chief/allpatients", AllPatientsDBImpl.class);
        router.attach("/chief/alldoctors", AllDoctorsDBImpl.class);
        router.attach("/chief/patients", AllPatientsListImpl.class);
        router.attach("/chief/doctors", AllDoctorsListImpl.class);
        router.attach("/chief/inactivedoctors", InactiveDoctorsImpl.class);
        router.attach("/chief/inactivepatients", InactivePatientsImpl.class);
        router.attach("/chief/allconsultations", ConsultationListResourceImpl.class);
        router.attach("/chief/settings/update", ChiefResourceImpl.class);
        router.attach("/chief/consultablepatients", AllConsultablePatientListResourceImpl.class);

        //patient Endpoints
        router.attach("/patient/{patient_id}", PatientResourceImpl.class);
        router.attach("/patient/{patient_id}/settings", PatientResourceImpl.class);
        router.attach("/patient/{patient_id}/addpatientrecord", PatientRecordsListImpl.class);
        router.attach("/patient/{patient_id}/allpatientrecords", PatientRecordsListImpl.class);
        router.attach("/patient/{patient_id}/patientRecord/{record_id}", PatientRecordResourceImpl.class);
        router.attach("/patient/{patient_id}/consultations", PatientConsultationsResourceImpl.class);
        /*
        * This endpoint allow the patient to view a consultation by its ID
        * router.attach("/consultation/{id}", ConsultationResourceImpl.class);
         */

        //doctor Endpoints
        router.attach("/doctor/{doctor_id}", DoctorResourceImpl.class);
        router.attach("/doctor/{doctor_id}/mypatients", MyPatientsResourceImpl.class);
        router.attach("/doctor/{doctor_id}/consultations",DoctorConsultationsImpl.class);
        /*
        this is the endpoint to view all the patients records per patient ID
        router.attach("/patient/{id}/storeData/allData", PatientRecordsListImpl.class);
        */
        router.attach("/doctor/undertake/patient", DoctorSelectionResourceImpl.class);
        router.attach("/doctor/{doctor_id}/consultablepatients",ConsultablePatientsByDoctorImpl.class);
        router.attach("/doctor/{doctor_id}/available-patients", AllAvailablePatientListResourceImpl.class);

        //Doctor Endpoints -- Consultations
        router.attach("/consultation/doctor/{doctor_id}/patient/{patient_id}", AddConsultationResourceImpl.class);
        router.attach("/consultation/{consultation_id}", ConsultationResourceImpl.class);
        router.attach("/consultation/{consultation_id}/doctor/{doctor_id}", UpdateConsultationResource.class);

        //soft Deletes
        router.attach("/patient/{patient_id}/settings/softDelete", SoftDeletePatientImpl.class);
        router.attach("/doctor/{doctor_id}/settings/softDelete", SoftDeleteDoctorImpl.class);

        return router;
    }
}
