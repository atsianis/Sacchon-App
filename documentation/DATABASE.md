| Patients             | Variable type    |
| :-------------:      |  :-------------: |
| id                   | Int, primary key |
| name                 | String           |
| email                | String           |
| surname              | String           |
| password             | String           |
| dob                  | Date             |
| gender               | Enum             |
| doctor_id            | Int              |
| can_be_examined      | Boolean          |
| consultation         | JSON             |
| last_active          | Date             |
| is_deleted           | Boolean          |


--------

| Doctors         | Variable type    |
| :-------------: |  :-------------: |
| id              | Int, primary key |
| is_deleted      | Boolean          |
| name            | String           |
| email           | String           |
| surname         | String           |
| password        | String           |
| patientList     | JSON             |
| consults        | JSON             |
| last_active     | Date             |

--------

| Chiefs          | Variable type    |
| :-------------: |  :-------------: |
| id              | Int, primary key |
| name            | String           |
| email           | String           |
| surname         | String           |
| password        | String           |

--------

| Patient_records | Variable type    |
| :-------------: |  :-------------: |
| id              | Int, primary key |
| sacchon         | Float            |
| calories        | Float            |
| created_at      | Date             |
| patient_id      | Int              |

----

| Consultations   | Variable type    |
| :-------------: |  :-------------: |
| id              | Int, primary key |
| patient_id      | Int              |
| doctor_id       | Int              |
| created_at      | Date             |
| patient_records | List of Ids      |
| comment         | String           |
| seen_by_patient | Date             |
