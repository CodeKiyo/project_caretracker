package com.mobdeve.caretracker

class PrescriptionDataGenerator {
    fun generateSampleData(): List<PrescriptionModel>{
        return listOf(
            PrescriptionModel(
                medName = "lipitor",
                dosage = "20mg",
                sig = "i"
            )
        )
    }
}