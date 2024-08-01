package com.mobdeve.caretracker

data class MedInfoModel (
    val patientBloodPressure: String,
    val patientBodyTemperature: String,
    val patientChiefComplaint: String,
    val patientComments: String,
    val patientDiagnosis: String,
    val patientHeartRate: String,
    val patientObjectives: String,
    val patientPlan: String,
    val patientRespirationRate: String,
    val patientWeight: Float
)