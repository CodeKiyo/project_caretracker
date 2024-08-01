package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.TestResultActivity.Companion.patientID
import com.mobdeve.caretracker.databinding.MedInfoEditPageBinding
import com.mobdeve.caretracker.databinding.MedInfoPageBinding
import com.mobdeve.caretracker.databinding.TestResultsPageBinding

class MedInfoActivity : AppCompatActivity() {
    private lateinit var medinfoPage: MedInfoPageBinding
    private lateinit var medinfoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        medinfoPage = MedInfoPageBinding.inflate(layoutInflater)
        setContentView(medinfoPage.root)

        medinfoRecyclerView = medinfoPage.medicalInfoRecyclerview
        medinfoRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val patientBloodPressure = MyFirestoreReferences.PATIENTBLOODPRESSURE_FIELD
        val patientBodyTemperature = MyFirestoreReferences.PATIENTBODYTEMPERATURE_FIELD
        val patientChiefComplaint = MyFirestoreReferences.PATIENTCHIEFCOMPLAINT_FIELD
        val patientComments = MyFirestoreReferences.PATIENTCOMMENTS_FIELD
        val patientDiagnosis = MyFirestoreReferences.PATIENTDIAGNOSIS_FIELD
        val patientHeartRate = MyFirestoreReferences.PATIENTHEARTRATE_FIELD
        val patientObjectives = MyFirestoreReferences.PATIENTOBJECTIVES_FIELD
        val patientPlan = MyFirestoreReferences.PATIENTPLAN_FIELD
        val patientRespirationRate = MyFirestoreReferences.PATIENTRESPIRATIONRATE_FIELD
        val patientWeight = MyFirestoreReferences.PATIENTWEIGHT_FIELD

        // Update verticalRecyclerView
        val db = Firebase.firestore
        val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
        val patientID = intent.getStringExtra(patientID).toString()
        val patientRef = patientCollection.document(patientID)
        val testResultsRef = patientRef.collection("Medical Information")
        val data = ArrayList<MedInfoModel>()

        testResultsRef.get().addOnSuccessListener { result ->
            for (document in result!!.documents) {
                val newData = MedInfoModel(
                    document.get(patientBloodPressure).toString(),
                    document.get(patientBodyTemperature).toString(),
                    document.get(patientChiefComplaint).toString(),
                    document.get(patientComments).toString(),
                    document.get(patientDiagnosis).toString(),
                    document.get(patientHeartRate).toString(),
                    document.get(patientObjectives).toString(),
                    document.get(patientPlan).toString(),
                    document.get(patientRespirationRate).toString(),
                    document.get(patientWeight).toString().toFloat())
                data.add(newData)
                System.out.println("test")
            }
            medinfoRecyclerView.adapter = MedInfoAdapter(data)
            medinfoRecyclerView.adapter?.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }

    }
}