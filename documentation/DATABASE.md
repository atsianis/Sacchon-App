| Patients             | Variable type    |
| :-------------:      |  :-------------: |
| id                   | Int, primary key |
| firstName            | String           |
| email                | String           |
| lastName             | String           |
| password             | String           |
| dob                  | Date             |
| gender               | Enum             |
| doctor_id            | Int              |
| canBeEexamined       | Boolean          |
| lastActive           | Date             |
| isDeleted            | Boolean          |


--------

| Doctors         | Variable type    |
| :-------------: |  :-------------: |
| id              | Int, primary key |
| isDeleted       | Boolean          |
| firstName       | String           |
| email           | String           |
| lastName        | String           |
| password        | String           |
| lastActive      | Date             |

--------

| Chiefs          | Variable type    |
| :-------------: |  :-------------: |
| id              | Int, primary key |
| firstName       | String           |
| email           | String           |
| lastName        | String           |
| password        | String           |

--------

| Patient_records | Variable type    |
| :-------------: |  :-------------: |
| id              | Int, primary key |
| glycose         | Float            |
| carbs           | Float            |
| timeCreated     | Date             |
| patient_id      | Int              |

----

| Consultations   | Variable type    |
| :-------------: |  :-------------: |
| id              | Int, primary key |
| patient_id      | Int              |
| doctor_id       | Int              |
| timeCreated     | Date             |
| comment         | String           |
| seenByPatient   | Date             |
