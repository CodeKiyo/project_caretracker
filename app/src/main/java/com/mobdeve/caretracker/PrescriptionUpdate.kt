package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.caretracker.databinding.PrescriptionUpdatePageBinding

class PrescriptionUpdate : AppCompatActivity() {
    private lateinit var binding: PrescriptionUpdatePageBinding
    private lateinit var firestore: FirebaseFirestore
    private var prescriptionId: String? = null
    private var patientId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrescriptionUpdatePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        // Retrieve data from Intent
        prescriptionId = intent.getStringExtra("prescriptionId")
        patientId = intent.getStringExtra(PrescriptionActivity.PATIENT_ID)
        val medName = intent.getStringExtra("medName").toString()
        val dosage = intent.getStringExtra("dosage").toString()
        val sig = intent.getStringExtra("sig").toString()

        // Set initial values in EditText fields
        binding.editMedName.text = Editable.Factory.getInstance().newEditable(medName)
        binding.editDosage.text = Editable.Factory.getInstance().newEditable(dosage)
        binding.editSig.text = Editable.Factory.getInstance().newEditable(sig)

        // Set up save button click listener
        binding.saveButt.setOnClickListener {
            updatePrescription()
        }
    }

    private fun updatePrescription() {
        val updatedMedName = binding.editMedName.text.toString().trim()
        val updatedDosage = binding.editDosage.text.toString().trim()
        val updatedSig = binding.editSig.text.toString().trim()

        // Validate input
        if (updatedMedName.isEmpty() || updatedDosage.isEmpty() || updatedSig.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a map for the updated prescription data
        val updatedPrescription = hashMapOf(
            "patientMedicine" to updatedMedName,
            "patientDosage" to updatedDosage,
            "patientSig" to updatedSig
        )

        // Update data in Firestore
        firestore.collection("Patients")
            .document(patientId!!)
            .collection("Prescription")
            .document(prescriptionId!!)
            .set(updatedPrescription)
            .addOnSuccessListener {
                Toast.makeText(this, "Prescription updated successfully", Toast.LENGTH_SHORT).show()

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
                Toast.makeText(this, "Error updating prescription: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                e.printStackTrace()  // Log the stack trace for debugging
            }
    }
}
