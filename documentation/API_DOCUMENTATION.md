# Getting Started

We basically need first of all to get the user to browse our application. So we need some endpoints to have the client to visit in order to navigate appropriately


Endpoints for login
--------------------------

```
POST    /login/chief
POST    /login/doctor
POST    /login/patient
```
**Description**: Login for all users

Register Endpoints
--------------------------

```
POST    /register/patient
POST    /chief/register/doctor
```
**Description**: Register for Patients and Doctors

Chief Endpoints
--------------------------

```
GET    /chief/allpatients
```
**Description**: Chief can display all patients

```
GET    /chief/alldoctorstor
```
**Description**: Chief can display all doctors

```
GET    /chief/patients
```
**Description**: Chief can display all patients

```
GET    /chief/doctors
```
**Description**: Chief can display all doctors

```
GET    /chief/inactivedoctors
```
**Description**: Chief can display all inactive doctors

```
GET    /chief/inactivepatients
```
**Description**: Chief can display all inactive patients

```
GET    /chief/allconsultations
```
**Description**: Chief can display all consultations

```
PUT    /chief/{chief_id}/settings/update
```
**Description**: Chief can modify his account

```
GET    /chief/consultablepatients
```
**Description**: Chief can display all consultable patients

Patient Endpoints
--------------------------

```
GET     /patient/{patient_id}
```
**Description**: Display the specific Patient

```
PUT     /patient/{patient_id}/settings
```
**Description**: Display the specific patient settings

```
POST    /patient/{patient_id}/addpatientrecord
```
**Description**: Add a patient record 

```
POST    /patient/{patient_id}/allpatientrecords
```
**Description**: Display all patient's records

```
GET     /patient/{patient_id}/patientRecord/{record_id}
```
**Description**: Display specific patient's record

```
GET     /patient/{patient_id}/consultations
```
**Description**: Display all patient's consultations 

```
PUT     /patient/{patient_id}/consultation/{consultation_id}/read
```
**Description**: Patient read consultation


Doctor Endpoints
--------------------------

```
GET    /doctor/{doctor_id}
```
**Description**: Display specific doctor

```
GET    /doctor/{doctor_id}/mypatients
```
**Description**: Display all doctor's patients

```
GET    /doctor/{doctor_id}/consultations
```
**Description**: Display all doctor's consultastions

```
PUT    /doctor/undertake/patient
```
**Description**: Doctor undertake a available patient

```
GET    /doctor/{doctor_id}/consultablepatients
```
**Description**: Display all doctor's consultable patients

```
GET    /doctor/{doctor_id}/available-patients
```
**Description**: Display available patients


Consultations Endpoints
--------------------------

```
POST    /consultation/doctor/{doctor_id}/patient/{patient_id}
```
**Description**: Add patient's consultation

```
GET     /consultation/{consultation_id}
```
**Description**: Display specific consultation

```
PUT     /consultation/{consultation_id}/doctor/{doctor_id}
```
**Description**: Modify patient's consultation

Soft Delete Endpoints
--------------------------

```
PUT    /patient/{patient_id}/settings/softDelete
PUT    /doctor/{doctor_id}/settings/softDelete
```
**Description**: Soft Delete users


