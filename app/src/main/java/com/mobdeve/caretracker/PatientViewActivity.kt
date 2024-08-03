package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.MedInfoActivity.Companion
import com.mobdeve.caretracker.databinding.PatientPersonalInfoBinding

class PatientViewActivity : AppCompatActivity()  {
    companion object{
        const val patientName : String = "PATIENT_NAME"
        const val patientAge : String = "PATIENT_AGE"
        const val patientBirthdate : String = "PATIENT_BIRTHDATE"
        const val patientECN : String = "PATIENT_ECN"
        const val patientECNo : String = "PATIENT_ECNO"
        const val patientEmail : String = "PATIENT_EMAIL"
        const val patientMobileno : String = "PATIENT_MOBILENO"
        const val patientNationality : String = "PATIENT_NATIONALITY"
        const val patientReligion : String = "PATIENT_RELIGION"
        const val patientSex : String = "PATIENT_SEX"
        const val patientCivilStatus : String = "PATIENT_CIVILSTATUS"
        const val patientRoom : String = "PATIENT_ROOM"
    }
    private lateinit var patientinfoPage: PatientPersonalInfoBinding
    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        patientinfoPage = PatientPersonalInfoBinding.inflate(layoutInflater)
        setContentView(patientinfoPage.root)
        getPatientDocumentIDs()

        userId = intent.getStringExtra("USER_ID").toString()

        // Replaces the username with what the user inputs from the Sign Up Page
        this.patientinfoPage.patientPersonalName.text = intent.getStringExtra(patientName).toString()
        this.patientinfoPage.patientAge.text = intent.getIntExtra(patientAge, 0).toString()
        this.patientinfoPage.patientBirthdate.text = intent.getStringExtra(patientBirthdate).toString()
        this.patientinfoPage.patientEmergencyContactName.text = intent.getStringExtra(patientECN).toString()
        this.patientinfoPage.patientEmergencyContactNo.text = intent.getStringExtra(patientECNo).toString()
        this.patientinfoPage.patientEmail.text = intent.getStringExtra(patientEmail).toString()
        this.patientinfoPage.patientMobileno.text = intent.getStringExtra(patientMobileno).toString()
        this.patientinfoPage.patientNationality.text = intent.getStringExtra(patientNationality).toString()
        this.patientinfoPage.patientReligion.text = intent.getStringExtra(patientReligion).toString()
        this.patientinfoPage.patientSex.text = intent.getStringExtra(patientSex).toString()
        this.patientinfoPage.patientCivilStatus.text = intent.getStringExtra(patientCivilStatus).toString()
        this.patientinfoPage.patientTestresults.setOnClickListener {
            val intent = Intent(this, TestResultActivity::class.java)
            intent.putExtra("PATIENT_ID", this.patientinfoPage.patientID.text)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
        this.patientinfoPage.patientMedinfo.setOnClickListener {
            val intent = Intent(this, MedInfoActivity::class.java)
            intent.putExtra("PATIENT_ID", this.patientinfoPage.patientID.text)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
        this.patientinfoPage.patientPrescription.setOnClickListener {
            val intent = Intent(this, PrescriptionActivity::class.java)
            intent.putExtra("PATIENT_ID", this.patientinfoPage.patientID.text)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
        this.patientinfoPage.backbutton.setOnClickListener {
            finish()
        }


        //this.patientinfoPage.patientSex.text = intent.getStringExtra(patientSex).toString()

    }

    fun getPatientDocumentIDs() {
        // Initialize Firestore
        val db = Firebase.firestore
        val name = intent.getStringExtra(patientName).toString()
        var documentId: String? = null

        // Reference to the Patients collection
        val patientsCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)

        patientsCollection
            .whereEqualTo(MyFirestoreReferences.PATIENTNAME_FIELD, name)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    for (document in querySnapshot.documents) {
                        this.patientinfoPage.patientID.text = document.id
                        this.patientinfoPage.patientID.visibility = View.GONE
                        val test = this.patientinfoPage.patientID.text
                        println(test)
                        return@addOnSuccessListener
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
}