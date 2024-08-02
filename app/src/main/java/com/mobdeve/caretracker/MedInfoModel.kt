package com.mobdeve.caretracker

import com.google.firebase.Timestamp

data class MedInfoModel (
    val patientDate: String,
    val patientWeight: String,
    val patientHeartRate: String,
    val patientBloodPressure: String,
    val patientBodyTemperature: String,
    val patientRespirationRate: String,

    val patientChiefComplaint: String,
    val patientObjectives: String,
    val patientDiagnosis: String,
    val patientPlan: String,
    val patientComments: String,

    val id: String = ""
)