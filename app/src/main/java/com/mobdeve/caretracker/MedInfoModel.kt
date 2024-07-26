package com.mobdeve.caretracker

data class MedInfoModel (
    val heartRate: Int,
    val bloodPressure: Float,
    val bodyTemp: Float,
    val respRate: Float,

    val chiefComp: String,
    val objectives: String,
    val diagnosis: String,
    val plan: String,
    val comments: String
)
