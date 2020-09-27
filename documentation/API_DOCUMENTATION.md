# Getting Started

We basically need first of all to get the user to browse our application. So we need some endpoints to have the client to visit in order to navigate appropriately


| URL        | Description      | Verb     |Params                       |
| ------     | ----             | ----     |:------:                     |
| "/"        | starter page     | **GET**  | -                           |
| "/login"   | login page       | **GET**  | -                           |
| "/login"   | login submit     | **POST** | email, password             |
| "/signup"  | starter page     | **GET**  | -                           |
| "/signup"  | starter page     | **POST** | name,surname,email,password |
| "/home"    | after login page | **GET**  | -                           |

-----

So now that we handled our basic routing to set a user or sign him up we need to redirect appropriately for each user type. Let's preview the endpoints for the chief doctor:

| URL                  | Description                                         | Verb     | Params                        |
| ------               | ----                                                | ----     |:------:                       |
| "/patients"          | Get all patients with filters for the required info | **GET**  |                               |
| "/patient/{id}"      | Get patient by id (Not sure if needs implement)     | **GET**  |                               |
| "/doctors"           | Get all the doctors according to the criteria       | **GET**  |                               |
| "/consultations"     | Get all consultations according to the criteria     | **GET**  |                               |
| "/createdoctor"      | Add new doctor to the database                      | **POST** | name,surname,email,password   |


---

These are the basic endpoints for the patient and all his/her actions

| URL                  | Description                                               | Verb        | Params                      |
| ------               | ----                                                      | ----        |     ---                     |
| "/patient/{id}"      | patient profile page                                      | **GET**     | -                           |
| "/settings"          | patient settings page                                     | **GET**     | -                           |
| "/settings"          | patient settings update                                   | **PUT**     | id, patient                 |
| "/settings"          | patient delete profile                                    | **DELETE**  | id                          |
| "/storedata"         | add new patient record landing page                       | **GET**     | -                           |
| "/storedata"         | add data for a new patient record                         | **POST**    | id, glycose, carbs       |
| "/updatestoreddata"  | edit/delete data from a patient record landing page       | **GET**     | -                           |
| "/updatestoreddata"  | edit a patient record                                     | **POST**    | id, patient_record          |
| "/updatestoreddata"  | delete a patient record                                   | **DELETE**  | id                          |

---

These are the basic endpoints for the doctor and all his/her actions

| URL                     | Description                                                | Verb        | Params                      |
| ------                  | ----                                                       | ----        |     ---                     |
| "/profile"              | doctor profile page                                        | **GET**     | -                           |
| "/settings"             | doctor settings page                                       | **GET**     | -                           |
| "/settings"             | doctor settings update                                     | **PUT**     | id, doctor                  |
| "/settings"             | doctor delete profile                                      | **DELETE**  | id                          |
| "/mypatients"           | doctor's patient list                                      | **GET**     | -                           |
| "/availablepatients"    | doctor's list of patients that are new to the system       | **GET**     | -                           |
| "/patient/{id}"         | patient profile page                                       | **GET**     | -                           |
| "/patient/{id}/consult" | create consultation for a patient landing page             | **GET**     | -                           |
| "/patient/{id}/consult" | add new consultation                                       | **POST**    | id, glycose, carbs       |
| "/patient/{id}/consult" | edit a consultation                                        | **PUT**     | id, consultation            |
| "/patient/{id}/consult" | delete a consultation                                      | **DELETE**  | id                          |
