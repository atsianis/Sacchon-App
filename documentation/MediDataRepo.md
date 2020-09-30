Doctor Documentation 
=======================
[![](https://img.shields.io/badge/User-patient-yellow)](https://github.com/codehub-learn/pfizer-se-team3)

Description
============
The role of the patient is to record glucose and carbohydrate levels in order to be examined by the doctor who will select the specific patient.

Requirements
============
* Manage their account
* Store their data
* View graphs
* Update their incorrect records 

Model
============
| Type                        | Fields             | Description                                     |
| ------                      | ----               | ----                                            |
|  **Long**                   | id                 | used to verify a person's identity in database  | 
|  **String**                 | firstName          | Personal Data                                   | 
|  **String**                 | lastName           | Personal Data                                   | 
|  **String**                 | email              | Personal Data                                   | 
|  **String**                 | password           | Personal Data                                   | 
|  **Date**                   | dob                | Date of Birthday                                | 
|  **Boolean**                | canBeExamined      | If Patient has examined                         | 
|  **Boolean**                | isDeleted          | If Patient is deleted                           |
|  **Date**                   | timeCreated        | The time that Patient created his profile       | 
|  **Date**                   | lastActive         | Last active patient                             | 
|  **String**                 | Gender             | Personal Data                                   | 
|  **Doctor**                 | doctor             | Patient's Doctor                                | 
|  **List<PatientRecord>**    | patientsRecords    | List of Patient's Records                       | 
|  **List<Consultations>**    | consultations      | List of Doctor's Consultations                  | 

Patients's Operations
============
1. The patient can access the doctor's consultation
2. The patient should enter data for one month. The user becomes inactive in case the month has not been completed and he has not entered data for 15 days
3. He can also change the personal data it has entered
4. The patient profile shows the graph of the data he has entered in the period of one month.

Endpoints
============
```java
//patient Endpoints
router.attach("/patient/{id}", PatientResourceImpl.class);
router.attach("/patient/{id}/settings", PatientResourceImpl.class);
router.attach("/patient/{id}/addpatientrecord", PatientRecordsListImpl.class);
router.attach("/patient/{id}/allpatientrecords", PatientRecordsListImpl.class);
router.attach("/patient/{pid}/patientRecord/{rid}", PatientRecordResourceImpl.class);
router.attach("/patient/{id}/consultations", PatientConsultationsResourceImpl.class);
```
