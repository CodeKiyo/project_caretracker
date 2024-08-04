package com.mobdeve.caretracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.MedInfoActivity.Companion.PATIENT_ID
import com.mobdeve.caretracker.databinding.PrescriptionAddPageBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class PrescriptionAdd : AppCompatActivity() {
    private lateinit var binding: PrescriptionAddPageBinding
    private lateinit var firestore: FirebaseFirestore
    private var patientId: String? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrescriptionAddPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        // Get patient ID from intent
        patientId = intent.getStringExtra(PrescriptionActivity.PATIENT_ID)
        userId = intent.getStringExtra(PrescriptionActivity.USER_ID)
        println(userId)

        if (patientId == null) {
            Toast.makeText(this, "Patient ID is missing", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if patient ID is missing
            return
        }

        binding.saveButt.setOnClickListener {
            savePrescription()
        }
        binding.backbutton.setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun savePrescription() {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val prescriptionDate = currentDate.format(formatter)
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
            "prescriptionDate" to prescriptionDate,
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
                val db = Firebase.firestore

                val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
                val patientRef =
                    patientCollection.document(intent.getStringExtra(PATIENT_ID).toString())

                patientRef.get()
                    .addOnSuccessListener { result ->
                        val firestore = FirebaseFirestore.getInstance()

                        val notInfo = hashMapOf(
                            "date" to SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(
                                Date()
                            ).toString(),
                            "type" to "Prescription",
                            "name" to result.get("patientName").toString(),
                            "oper" to "Added"
                        )

                        firestore.collection("Users")
                            .document(userId!!)
                            .collection("Notification")
                            .add(notInfo)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Prescription added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

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
                                    "Error adding health record: ${e.localizedMessage}",
                                    Toast.LENGTH_LONG
                                ).show()
                                e.printStackTrace()  // Log the stack trace for debugging
                            }
                    }
            }
    }
}
