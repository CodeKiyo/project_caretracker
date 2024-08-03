package com.mobdeve.caretracker

import TestResultAdapter
import TestResultDataGenerator
import TestResultModel
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientAge
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientBirthdate
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientCivilStatus
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientECN
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientECNo
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientEmail
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientMobileno
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientName
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientNationality
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientReligion
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientSex
import com.mobdeve.caretracker.R
import com.mobdeve.caretracker.databinding.PatientPersonalInfoBinding
import com.mobdeve.caretracker.databinding.TestResultsPageBinding
import com.mobdeve.caretracker.databinding.TestResultsRecycleritemBinding

class TestResultActivity : AppCompatActivity() {
    companion object{
        const val resultComponents : String = "RESULT_COMPONENTS"
        const val resultDate : String = "RESULT_DATE"
        const val resultFindings : String = "RESULT_FINDINGS"
        const val resultImage : String = "RESULT_IMAGE"
        const val resultStatus : String = "RESULT_STATUS"
        const val resultComments : String = "RESULT_COMMENTS"
        const val testType : String = "TEST_TYPE"
        const val patientID : String = "PATIENT_ID"
    }
    private lateinit var testresultsPage: TestResultsPageBinding
    private lateinit var testResultsRecyclerView: RecyclerView
    private lateinit var myTestResultAdapter: TestResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        testresultsPage = TestResultsPageBinding.inflate(layoutInflater)
        setContentView(testresultsPage.root)

        testResultsRecyclerView = testresultsPage.testResultsRecyclerview
        testResultsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val resultComments = MyFirestoreReferences.RESULTCOMMENTS_FIELD
        val resultComponents = MyFirestoreReferences.RESULTCOMPONENTS_FIELD
        val resultDate = MyFirestoreReferences.RESULTDATE_FIELD
        val resultFindings = MyFirestoreReferences.RESULTFINDINGS_FIELD
        val resultImage = MyFirestoreReferences.RESULTIMAGE_FIELD
        val resultStatus = MyFirestoreReferences.RESULTSTATUS_FIELD

        // Update verticalRecyclerView
        val db = Firebase.firestore
        val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
        val patientID = intent.getStringExtra(patientID).toString()
        val patientRef = patientCollection.document(patientID)
        val testResultsRef = patientRef.collection("Test Results")
        val finalRef = testResultsRef.document("CBC")
        val data = ArrayList<TestResultModel>()

        testResultsRef.get().addOnSuccessListener { result ->
            for (document in result!!.documents) {
                val newData = TestResultModel(
                    document.id,
                    document.get(resultDate).toString(),
                    document.get(resultStatus).toString(),
                    document.get(resultFindings).toString(),
                    document.get(resultComponents).toString(),
                    document.get(resultComments).toString(),
                    R.drawable.default_photo)
                data.add(newData)
                System.out.println("test")
            }
            testResultsRecyclerView.adapter = TestResultAdapter(data)
            testResultsRecyclerView.adapter?.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }

        testresultsPage.backbutton.setOnClickListener() {
            finish()
        }

    }

    fun updateView() {
        val resultComments = MyFirestoreReferences.RESULTCOMMENTS_FIELD
        val resultComponents = MyFirestoreReferences.RESULTCOMPONENTS_FIELD
        val resultDate = MyFirestoreReferences.RESULTDATE_FIELD
        val resultFindings = MyFirestoreReferences.RESULTFINDINGS_FIELD
        val resultImage = MyFirestoreReferences.RESULTIMAGE_FIELD
        val resultStatus = MyFirestoreReferences.RESULTSTATUS_FIELD

        // Update verticalRecyclerView
        val db = Firebase.firestore
        val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
        val patientID = patientID
        val patientRef = patientCollection.document(patientID)
        val testResultsRef = patientRef.collection("Test Results")
        val finalRef = testResultsRef.document("CBC")
        val data = ArrayList<TestResultModel>()


        testResultsRef.get().addOnSuccessListener { result ->
            for (document in result!!.documents) {
                val newData = TestResultModel(
                    document.id,
                    document.get(resultDate).toString(),
                    document.get(resultStatus).toString(),
                    document.get(resultFindings).toString(),
                    document.get(resultComponents).toString(),
                    document.get(resultComments).toString(),
                    R.drawable.default_photo)
                data.add(newData)
                println(resultDate)
            }
            testResultsRecyclerView.adapter = TestResultAdapter(data)
            testResultsRecyclerView.adapter?.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }

    }
}
