package com.mobdeve.caretracker

data class PrescriptionModel (
    val prescriptionDate: String,
    val medName: String,
    val dosage: String,
    val sig: String,
    val id: String = "" // Add a default value for id
    )