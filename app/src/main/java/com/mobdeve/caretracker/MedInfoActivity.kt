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
import com.mobdeve.caretracker.PrescriptionActivity.Companion

class MedInfoActivity : AppCompatActivity() {
    companion object {
        const val PATIENT_ID: String = "PATIENT_ID"
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

        medinfoPage.addButt.setOnClickListener() {
            val patientID = intent.getStringExtra(PATIENT_ID)
            val intent = Intent(this, MedInfoAddActivity::class.java)
            intent.putExtra(PATIENT_ID, patientID)
            startActivity(intent)
        }
    }

    private fun fetchMedInfo () {
        val db = Firebase.firestore

        val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
        val patientRef = patientCollection.document(intent.getStringExtra(patientID).toString())
        val medInfoRef = patientRef.collection("Medical Information")

        val data = ArrayList<MedInfoModel>()

        medInfoRef.get()
            .addOnSuccessListener { result ->
                if (result != null && !result.isEmpty) {
                    data.clear()
                    for (document in result!!.documents) {
                        val newData = MedInfoModel(
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
                        MedInfoAdapter(data, intent.getStringExtra(PATIENT_ID).toString(), { medInfoId ->
                            deleteAlert(medInfoId)
                        })
                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    private fun deleteAlert (medInfoId: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete Prescription")
            .setMessage("Are you sure you want to delete this record?")
            .setPositiveButton("Yes") { _, _ ->
                val patientID = intent.getStringExtra(PrescriptionActivity.PATIENT_ID).orEmpty()
                val db = Firebase.firestore
                val recordRef = db.collection("Patients").document(patientID).collection("Medical Information").document(medInfoId)

                recordRef.delete()
                    .addOnSuccessListener {
                        Log.d("MedInfoAdapter", "Record successfully deleted")
                        fetchMedInfo() // Refresh the list after deletion
                    }
                    .addOnFailureListener { exception ->
                        Log.e("MedInfoAdapter", "Error deleting document: $exception")
                    }
            }
            .setNegativeButton("No", null)
            .show()
    }
}