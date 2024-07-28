package com.mobdeve.caretracker

data class MedInfoModel (
    val date: String,
    val weight: Float,
    val heartRate: Int,
    val bloodPressure: String,
    val bodyTemp: Float,
    val respRate: Int,

    val chiefComp: String,
    val objectives: String,
    val diagnosis: String,
    val plan: String,
    val comments: String
)