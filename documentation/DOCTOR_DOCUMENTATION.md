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

