Doctor Documentation 
=======================
[![](https://img.shields.io/badge/User-Doctor-red)](https://github.com/codehub-learn/pfizer-se-team3)

Description
============
The goal is to create a doctor who will provide knowledge to the available patients. Essentially, the operation of the doctor is to advise the patient based on detailed data provided by the patient, such as glucose and carbs, during period of one month.

Requirements
============
* Μanage their account
* View patient record
* Search patients
* Consult patients
* Update patients' consultations

Model
============
| Type                        | Fields             | Description                                     |
| ------                      | ----               | ----                                            |
|  **Long**                   | id                 | used to verify a person's identity in database  | 
|  **String**                 | firstName          | Personal Data                                   | 
|  **String**                 | lastName           | Personal Data                                   | 
|  **String**                 | email              | Personal Data                                   | 
|  **String**                 | password           | Personal Data                                   | 
|  **Date**                   | lastActive         | If Doctor is active                             | 
|  **Boolean**                | isDeleted          | If Doctor is deleted                            | 
|  **List<Patients>**         | patients           | List of Patients                                | 
|  **List<Consultations>**    | consultations      | List of Patient's Consultations                 | 

Doctor's Operations
============
1. The Doctor can create and store medical advice for one or more patients. The Doctor has the ability to select patients who do not yet have a doctor.
2. The Doctor has the ability to see all available patients. Available patients are those who have completed one month of recording data and do not yet have a doctor.
3. He can also see all the patients he has chosen to assess their health.
4. He can modify his profile whenever he wants as well as the consultations he has saved

Endpoints
============
```java
 //doctor Endpoints
router.attach("/doctor/{id}", DoctorResourceImpl.class);
router.attach("/doctor/{id}/mypatients", MyPatientsResourceImpl.class);
router.attach("/doctor/{id}/consultations",DoctorConsultationsImpl.class);
/*
this is the endpoint to view all the patients records per patient ID
router.attach("/patient/{id}/storeData/allData", PatientRecordsListImpl.class);
*/
router.attach("/doctor/{did}/select/{pid}", DoctorSelectionResourceImpl.class);
router.attach("/doctor/{id}/consultablepatients", AllConsultablePatientListResourceImpl.class);
router.attach("/doctor/{id}/available-patients", AllAvailablePatientListResourceImpl.class);
//Doctor Endpoints -- Consultations
router.attach("/consultation/doctor/{did}/patient/{pid}", AddConsultationResourceImpl.class);
router.attach("/consultation/{id}", ConsultationResourceImpl.class);
router.attach("/consultation/{cid}/doctor/{did}", UpdateConsultationResource.class);
```


