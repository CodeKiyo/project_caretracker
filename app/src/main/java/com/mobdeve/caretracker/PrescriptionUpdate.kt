package com.mobdeve.caretracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.MedInfoActivity.Companion.PATIENT_ID
import com.mobdeve.caretracker.databinding.PrescriptionUpdatePageBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class PrescriptionUpdate : AppCompatActivity() {
    private lateinit var binding: PrescriptionUpdatePageBinding
    private lateinit var firestore: FirebaseFirestore
    private var prescriptionDate: String? = null
    private var prescriptionId: String? = null
    private var patientId: String? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrescriptionUpdatePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        // Retrieve data from Intent
        prescriptionDate = intent.getStringExtra("prescriptionDate")
        prescriptionId = intent.getStringExtra("prescriptionId")
        patientId = intent.getStringExtra(PrescriptionActivity.PATIENT_ID)
        userId = intent.getStringExtra(PrescriptionActivity.USER_ID)

        val medName = intent.getStringExtra("medName")?.toString() ?: ""
        val dosage = intent.getStringExtra("dosage")?.toString() ?: ""
        val sig = intent.getStringExtra("sig")?.toString() ?: ""

        // Set initial values in EditText fields
        binding.editMedName.text = Editable.Factory.getInstance().newEditable(medName)
        binding.editDosage.text = Editable.Factory.getInstance().newEditable(dosage)
        binding.editSig.text = Editable.Factory.getInstance().newEditable(sig)

        // Set up save button click listener
        binding.saveButt.setOnClickListener {
            updatePrescription()
        }
        binding.backbutton.setOnClickListener() {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePrescription() {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val updatedDate = currentDate.format(formatter)
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
            "prescriptionDate" to updatedDate,
            "patientMedicine" to updatedMedName,
            "patientDosage" to updatedDosage,
            "patientSig" to updatedSig
        )

        // Update data in Firestore
        if (patientId != null && prescriptionId != null) {
            firestore.collection("Patients")
                .document(patientId!!)
                .collection("Prescription")
                .document(prescriptionId!!)
                .set(updatedPrescription)
                .addOnSuccessListener {
                    val db = Firebase.firestore

                    val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
                    val patientRef = patientCollection.document(patientId!!)

                    patientRef.get()
                        .addOnSuccessListener { result ->
                            val patientName = result.getString("patientName")
                            if (patientName != null) {
                                val notInfo = hashMapOf(
                                    "date" to SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Date()).toString(),
                                    "type" to "Prescription",
                                    "name" to patientName,
                                    "oper" to "Edited"
                                )

                                if (userId != null) {
                                    firestore.collection("Users")
                                        .document(userId!!)
                                        .collection("Notification")
                                        .add(notInfo)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Prescription updated successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            // Create an Intent to return to the previous activity
                                            val intent = Intent(this, PrescriptionActivity::class.java).apply {
                                                putExtra(PrescriptionActivity.PATIENT_ID, patientId)
                                                putExtra(PrescriptionActivity.USER_ID, userId)
                                                // Use flags to clear the back stack
                                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                            }
                                            startActivity(intent)
                                            finish() // Close the activity
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                this,
                                                "Error adding notification: ${e.localizedMessage}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            e.printStackTrace()  // Log the stack trace for debugging
                                        }
                                } else {
                                    Log.e("PrescriptionUpdate", "User ID is null")
                                    Toast.makeText(
                                        this,
                                        "User ID is missing",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Log.e("PrescriptionUpdate", "Patient name is null")
                                Toast.makeText(
                                    this,
                                    "Error fetching patient name",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error fetching patient: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                            e.printStackTrace()  // Log the stack trace for debugging
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error updating prescription: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()  // Log the stack trace for debugging
                }
        } else {
            Log.e("PrescriptionUpdate", "Patient ID or Prescription ID is null")
            Toast.makeText(
                this,
                "Patient ID or Prescription ID is missing",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
