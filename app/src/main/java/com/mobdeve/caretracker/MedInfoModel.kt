package com.mobdeve.caretracker

data class MedInfoModel (
    val recordNo: Int,
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