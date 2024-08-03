package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.TestResultActivity.Companion.patientID
import com.mobdeve.caretracker.databinding.MedInfoEditPageBinding
import com.mobdeve.caretracker.databinding.MedInfoPageBinding
import com.mobdeve.caretracker.databinding.TestResultsPageBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.caretracker.PrescriptionActivity.Companion
import java.text.SimpleDateFormat
import java.util.Date

class MedInfoActivity : AppCompatActivity() {
    companion object {
        const val PATIENT_ID: String = "PATIENT_ID"
        const val USER_ID: String = "USER_ID"
    }

    private lateinit var medinfoPage: MedInfoPageBinding
    private lateinit var medinfoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        medinfoPage = MedInfoPageBinding.inflate(layoutInflater)
        setContentView(medinfoPage.root)

        medinfoRecyclerView = medinfoPage.medicalInfoRecyclerview
        medinfoRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Update verticalRecyclerView
        fetchMedInfo()

        Toast.makeText(this, intent.getStringExtra(USER_ID).toString(), Toast.LENGTH_SHORT).show()
        medinfoPage.addButt.setOnClickListener() {
            val patientID = intent.getStringExtra(PATIENT_ID)
            val userID = intent.getStringExtra(USER_ID)
            val intent = Intent(this, MedInfoAddActivity::class.java)
            intent.putExtra(PATIENT_ID, patientID)
            intent.putExtra(USER_ID, userID)
            startActivity(intent)
        }
    }

    private fun fetchMedInfo () {
        val db = Firebase.firestore

        val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
        val patientRef = patientCollection.document(intent.getStringExtra(patientID).toString())
        val medInfoRef = patientRef.collection("Medical Information")

        val data = ArrayList<MedInfoModel>()
        val index = 0

        medInfoRef.get()
            .addOnSuccessListener { result ->
                if (result != null && !result.isEmpty) {
                    data.clear()
                    for (document in result!!.documents) {
                        val newData = MedInfoModel(
                            index + 1,
                            document.get(MyFirestoreReferences.PATIENTDATE_FIELD).toString(),
                            document.get(MyFirestoreReferences.PATIENTWEIGHT_FIELD).toString(),
                            document.get(MyFirestoreReferences.PATIENTHEARTRATE_FIELD).toString(),
                            document.get(MyFirestoreReferences.PATIENTBLOODPRESSURE_FIELD)
                                .toString(),
                            document.get(MyFirestoreReferences.PATIENTBODYTEMPERATURE_FIELD)
                                .toString(),
                            document.get(MyFirestoreReferences.PATIENTRESPIRATIONRATE_FIELD)
                                .toString(),
                            document.get(MyFirestoreReferences.PATIENTCHIEFCOMPLAINT_FIELD)
                                .toString(),
                            document.get(MyFirestoreReferences.PATIENTOBJECTIVES_FIELD).toString(),
                            document.get(MyFirestoreReferences.PATIENTDIAGNOSIS_FIELD).toString(),
                            document.get(MyFirestoreReferences.PATIENTPLAN_FIELD).toString(),
                            document.get(MyFirestoreReferences.PATIENTCOMMENTS_FIELD).toString(),
                            document.id
                        )
                        data.add(newData)
                        System.out.println("test")
                    }
                    medinfoRecyclerView.adapter =
                        MedInfoAdapter(data, intent.getStringExtra(PATIENT_ID).toString(), { medInfoId, userId ->
                            deleteAlert(medInfoId, userId)
                        }, intent.getStringExtra(USER_ID).toString())
                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    private fun deleteAlert (medInfoId: String, userId: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete Prescription")
            .setMessage("Are you sure you want to delete this record?")
            .setPositiveButton("Yes") { _, _ ->
                val patientID = intent.getStringExtra(PATIENT_ID).orEmpty()

                val db = Firebase.firestore
                val recordRef = db.collection("Patients").document(patientID).collection("Medical Information").document(medInfoId)

                recordRef.delete()
                    .addOnSuccessListener {
                        Log.d("MedInfoAdapter", "Record successfully deleted")

                        val db = Firebase.firestore

                        val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
                        val patientRef = patientCollection.document(intent.getStringExtra(PATIENT_ID).toString())

                        patientRef.get()
                            .addOnSuccessListener { result ->
                                val firestore = FirebaseFirestore.getInstance()

                                val notInfo = hashMapOf(
                                    "date" to SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(
                                        Date()
                                    ).toString(),
                                    "type" to "Medical Information",
                                    "name" to result.get("patientName").toString(),
                                    "oper" to "Deleted"
                                )

                                firestore.collection("Users")
                                    .document(userId!!)
                                    .collection("Notification")
                                    .add(notInfo)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Health Record added successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        fetchMedInfo() // Refresh the list after deletion
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
                    .addOnFailureListener { exception ->
                        Log.e("MedInfoAdapter", "Error deleting document: $exception")
                    }


            }
            .setNegativeButton("No", null)
            .show()
    }
}