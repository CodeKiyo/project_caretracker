package com.mobdeve.caretracker

import TestResultModel
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        const val username : String = "username"
    }
    private lateinit var patientinfoPage: PatientPersonalInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        patientinfoPage = PatientPersonalInfoBinding.inflate(layoutInflater)
        setContentView(patientinfoPage.root)
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

        this.patientinfoPage.patientPersonalStatictext1.setOnClickListener {
            val intent = Intent(this, TestResultActivity::class.java)
            startActivity(intent)
        }


        //this.patientinfoPage.patientSex.text = intent.getStringExtra(patientSex).toString()

    }
}