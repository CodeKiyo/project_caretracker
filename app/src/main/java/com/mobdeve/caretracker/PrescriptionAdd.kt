package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.caretracker.databinding.PrescriptionAddPageBinding

class PrescriptionAdd : AppCompatActivity() {
    private lateinit var binding: PrescriptionAddPageBinding
    private lateinit var firestore: FirebaseFirestore
    private var patientId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrescriptionAddPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        // Get patient ID from intent
        patientId = intent.getStringExtra(PrescriptionActivity.PATIENT_ID)

        if (patientId == null) {
            Toast.makeText(this, "Patient ID is missing", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if patient ID is missing
            return
        }

        binding.saveButt.setOnClickListener {
            savePrescription()
        }
    }

    private fun savePrescription() {
        // Retrieve user input
        val medicineName = binding.addMedicine.text.toString().trim()
        val dosage = binding.addDosage.text.toString().trim()
        val sig = binding.addSig.text.toString().trim()

        // Validate input
        if (medicineName.isEmpty() || dosage.isEmpty() || sig.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a map for the prescription data
        val prescription = hashMapOf(
            "patientMedicine" to medicineName,
            "patientDosage" to dosage,
            "patientSig" to sig
        )

        // Save data to Firestore in the patient's "Prescription" sub-collection
        firestore.collection("Patients")
            .document(patientId!!)
            .collection("Prescription")
            .add(prescription)
            .addOnSuccessListener {
                Toast.makeText(this, "Prescription added successfully", Toast.LENGTH_SHORT).show()

                // Create an Intent to return to the previous activity
                val intent = Intent(this, PrescriptionActivity::class.java).apply {
                    putExtra(PrescriptionActivity.PATIENT_ID, patientId)
                    // Use flags to clear the back stack
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                startActivity(intent)
                finish() // Close the activity
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding prescription: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                e.printStackTrace()  // Log the stack trace for debugging
            }
    }
}
