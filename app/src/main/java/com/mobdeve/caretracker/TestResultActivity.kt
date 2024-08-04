package com.mobdeve.caretracker

import TestResultAdapter
import TestResultModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.databinding.TestResultsPageBinding

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
        var index = 0

        testResultsRef.get().addOnSuccessListener { result ->
            for (document in result!!.documents) {
                val newData = TestResultModel(
                    index + 1,
                    document.id,
                    document.get(resultDate).toString(),
                    document.get(resultStatus).toString(),
                    document.get(resultFindings).toString(),
                    document.get(resultComponents).toString(),
                    document.get(resultComments).toString(),
                    document.get(resultImage).toString())
                data.add(newData)
                index++
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
}
