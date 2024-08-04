package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.databinding.PrescriptionPageBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class PrescriptionActivity : AppCompatActivity() {
    companion object {
        const val PATIENT_ID: String = "PATIENT_ID"
        const val USER_ID: String = "USER_ID"
    }

    private lateinit var binding: PrescriptionPageBinding
    private lateinit var myPrescriptionAdapter: PrescriptionAdapter
    private val prescriptions = ArrayList<PrescriptionModel>() // To hold prescriptions and their IDs
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrescriptionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        binding.prescriptionRecyclerview.layoutManager = LinearLayoutManager(this)
        userId = intent.getStringExtra(USER_ID)
        // Fetch prescriptions from Firestore
        fetchPrescriptions()

        // Set up the add button
        binding.addButton.setOnClickListener {

            val patientID = intent.getStringExtra(PATIENT_ID).orEmpty()
            val userID = intent.getStringExtra(USER_ID)
            val intent = Intent(this, PrescriptionAdd::class.java).apply {
                putExtra(PATIENT_ID, patientID)
                putExtra(USER_ID, userID)
            }
            startActivity(intent)
        }

        binding.backbutton.setOnClickListener() {
            finish()
        }

    }

    private fun fetchPrescriptions() {
        val db = Firebase.firestore
        val patientID = intent.getStringExtra(PATIENT_ID).orEmpty()
        val prescriptionsRef = db.collection("Patients").document(patientID).collection("Prescription")

        prescriptionsRef.get()
            .addOnSuccessListener { result ->
                if (result != null && !result.isEmpty) {
                    prescriptions.clear() // Clear existing list
                    for (document in result.documents) {
                        val prescription = PrescriptionModel(
                            result.documents.indexOf(document) + 1,
                            document.getString("prescriptionDate") ?: "",
                            document.getString("patientMedicine") ?: "",
                            document.getString("patientDosage") ?: "",
                            document.getString("patientSig") ?: "",
                            document.id // Store the document ID
                        )
                        prescriptions.add(prescription)
                    }
                    Log.d("PrescriptionActivity", "Fetched ${prescriptions.size} prescriptions")
                    setupAdapter()
                } else {
                    Log.d("PrescriptionActivity", "No prescriptions found")
                    binding.prescriptionRecyclerview.visibility = View.GONE

                }
            }
            .addOnFailureListener { exception ->
                Log.e("PrescriptionActivity", "Error getting documents: $exception")
            }
    }

    private fun setupAdapter() {
        myPrescriptionAdapter = PrescriptionAdapter(prescriptions, { prescriptionId ->
            showDeleteConfirmationDialog(prescriptionId)
        }, { prescription ->
            // Handle edit button click
            val intent = Intent(this, PrescriptionUpdate::class.java).apply {
                putExtra("prescriptionDate", prescription.prescriptionDate)
                putExtra("prescriptionId", prescription.id)
                putExtra("medName", prescription.medName)
                putExtra("dosage", prescription.dosage)
                putExtra("sig", prescription.sig)
                putExtra(PATIENT_ID, intent.getStringExtra(PATIENT_ID))
                putExtra(USER_ID, userId)
            }
            startActivity(intent)
        })
        binding.prescriptionRecyclerview.adapter = myPrescriptionAdapter
    }

    private fun showDeleteConfirmationDialog(prescriptionId: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete Prescription")
            .setMessage("Are you sure you want to delete this prescription?")
            .setPositiveButton("Yes") { _, _ ->
                deletePrescription(prescriptionId)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deletePrescription(prescriptionId: String) {
        val patientID = intent.getStringExtra(PATIENT_ID).orEmpty()
        val db = Firebase.firestore
        val prescriptionRef = db.collection("Patients").document(patientID).collection("Prescription").document(prescriptionId)

        prescriptionRef.delete()
            .addOnSuccessListener {
                Log.d("PrescriptionActivity", "Prescription successfully deleted")

                val patientRef = db.collection(MyFirestoreReferences.PATIENT_COLLECTION).document(patientID)

                patientRef.get()
                    .addOnSuccessListener { result ->
                        val firestore = FirebaseFirestore.getInstance()

                        val notInfo = hashMapOf(
                            "date" to SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Date()).toString(),
                            "type" to "Prescription",
                            "name" to result.getString("patientName").toString(),
                            "oper" to "Deleted"
                        )

                        firestore.collection("Users")
                            .document(userId!!)
                            .collection("Notification")
                            .add(notInfo)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Prescription deleted successfully", Toast.LENGTH_SHORT).show()
                                fetchPrescriptions() // Refresh the list after deletion
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error adding notification: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                                e.printStackTrace()  // Log the stack trace for debugging
                            }
                    }
            }
            .addOnFailureListener { exception ->
                Log.e("PrescriptionActivity", "Error deleting document: $exception")
            }
    }
}
