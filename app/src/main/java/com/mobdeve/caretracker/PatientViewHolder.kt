package com.mobdeve.caretracker

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PatientViewHolder(itemView: View, private val username: String, private val userId: String): RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.hm_patientlist_name)
    private val age: TextView = itemView.findViewById(R.id.hm_patientlist_age_number)
    private val room: TextView = itemView.findViewById(R.id.hm_patientlist_room_number)
    private val patientSexIcon: ImageView? = itemView.findViewById(R.id.hm_patientlist_sex)

    fun bindData(model: PatientModel) {
        this.name.text = model.patientName
        this.age.text = model.patientAge.toString()
        this.room.text = model.patientRoom.toString()
        if(model.patientSex== "F")
            this.patientSexIcon?.setImageResource(R.drawable.patient_female)

        val db = Firebase.firestore
        val usersRef = db.collection(MyFirestoreReferences.USERS_COLLECTION)
        val patients = MyFirestoreReferences.PATIENTLIST_FIELD
        val userPatientList = ArrayList<String>()

        usersRef
            .whereEqualTo(MyFirestoreReferences.NAME_FIELD, username)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val data = document.get(patients) as? ArrayList<String>
                    if (data != null) {
                        // Add all elements of data to dataArray
                        userPatientList.addAll(data)
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }

        this.itemView.setOnClickListener {
            println("clicked!")
            val intentToViewItem = Intent(itemView.context, PatientViewActivity::class.java)
            intentToViewItem.putExtra("PATIENT_NAME", model.patientName)
            intentToViewItem.putExtra("PATIENT_AGE", model.patientAge)
            intentToViewItem.putExtra("PATIENT_ROOM", model.patientRoom)
            intentToViewItem.putExtra("PATIENT_BIRTHDATE", model.patientBirthdate)
            intentToViewItem.putExtra("PATIENT_ECN", model.patientECN)
            intentToViewItem.putExtra("PATIENT_ECNO", model.patientECNo)
            intentToViewItem.putExtra("PATIENT_EMAIL", model.patientEmail)
            intentToViewItem.putExtra("PATIENT_MOBILENO", model.patientMobileno)
            intentToViewItem.putExtra("PATIENT_NATIONALITY", model.patientNationality)
            intentToViewItem.putExtra("PATIENT_RELIGION", model.patientReligion)
            intentToViewItem.putExtra("PATIENT_SEX", model.patientSex)
            intentToViewItem.putExtra("PATIENT_CIVILSTATUS", model.patientCivilStatus)
            intentToViewItem.putExtra("USER_ID", userId)
            (itemView.context as? Activity)?.startActivity(intentToViewItem, null)
        }
    }
}