# Getting Started

We basically need first of all to get the user to browse our application. So we need some endpoints to have the client to visit in order to navigate appropriately


Endpoints for login
--------------------------

```
**POST**    /login/chief
**POST**    /login/doctor
**POST**    /login/patient
```
Description: Type the command after the dollar sign and hit enter:

Register Endpoints
--------------------------

```
POST    /register/patient
POST    /chief/register/doctor
```
Description: Type the command after the dollar sign and hit enter:

Chief Endpoints
--------------------------

```
POST    /chief/allpatients
POST    /chief/alldoctorstor
POST    /chief/patients
POST    /chief/doctors
POST    /chief/inactivedoctors
POST    /chief/inactivepatients
POST    /chief/allconsultations
POST    /chief/{chief_id}/settings/update
POST    /chief/consultablepatients
```
Description: Type the command after the dollar sign and hit enter:

Patient Endpoints
--------------------------

```
POST    /patient/{patient_id}
POST    /patient/{patient_id}/settings
POST    /patient/{patient_id}/addpatientrecord
POST    /patient/{patient_id}/allpatientrecords
POST    /patient/{patient_id}/patientRecord/{record_id}
POST    /patient/{patient_id}/consultations
POST    /patient/{patient_id}/consultation/{consultation_id}/read
```
Description: Type the command after the dollar sign and hit enter:

Doctor Endpoints
--------------------------

```
POST    /doctor/{doctor_id}
POST    /doctor/{doctor_id}/mypatients
POST    /doctor/{doctor_id}/consultations
POST    /doctor/undertake/patient
POST    /doctor/{doctor_id}/consultablepatients
POST    /doctor/{doctor_id}/available-patients
```
Description: Type the command after the dollar sign and hit enter:

Consultations Endpoints
--------------------------

```
POST    /consultation/doctor/{doctor_id}/patient/{patient_id}
POST    /consultation/{consultation_id}
POST    /consultation/{consultation_id}/doctor/{doctor_id}
```
Description: Type the command after the dollar sign and hit enter:

Soft Delete Endpoints
--------------------------

```
POST    /patient/{patient_id}/settings/softDelete
POST    /doctor/{doctor_id}/settings/softDelete
```
Description: Type the command after the dollar sign and hit enter:


