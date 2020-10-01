Reporter Documentation 
=======================
[![](https://img.shields.io/badge/User-Chief-blue)](https://github.com/codehub-learn/pfizer-se-team3)

Description
============
The role of the reporter is the platform administrator. It has the ability to see all the data of the platform and also to delete and enter any user.

Requirements
============
* Remove/Update/Add
* View all Results

Model
============
| Type                        | Fields             | Description                                     |
| ------                      | ----               | ----                                            |
|  **Long**                   | id                 | used to verify a person's identity in database  | 
|  **String**                 | firstName          | Personal Data                                   | 
|  **String**                 | lastName           | Personal Data                                   | 
|  **String**                 | email              | Personal Data                                   | 
|  **String**                 | password           | Personal Data                                   | 


Chief's Operations
============
1. The information submissions (personal monitor data) of a patient over a time range
2. The information submissions (consultations) of a doctor over a time range
3. The list of the patients who are waiting for a consultation and the time elapsed since they needed to have one
4. The list of the patients with no activity over a time range
5. The list of the doctors with no activity over a time range

Endpoints
============
```java
//chief Endpoints
router.attach("/chief/allpatients", AllPatientsDBImpl.class);
router.attach("/chief/alldoctors", AllDoctorsDBImpl.class);
router.attach("/chief/patients", AllPatientsListImpl.class);
router.attach("/chief/doctors", AllDoctorsListImpl.class);
router.attach("/chief/inactivedoctors", InactiveDoctorsImpl.class);
router.attach("/chief/inactivepatients", InactivePatientsImpl.class);
router.attach("/chief/allconsultations", ConsultationListResourceImpl.class);
router.attach("/chief/settings/update", ChiefResourceImpl.class);
```
