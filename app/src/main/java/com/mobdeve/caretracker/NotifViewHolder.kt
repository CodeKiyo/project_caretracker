package com.mobdeve.caretracker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotifViewHolder (itemView: View, private val username: String) : RecyclerView.ViewHolder(itemView) {
    val notiName: TextView = itemView.findViewById(R.id.hm_patient_name)
    val notiIcon: ImageView = itemView.findViewById(R.id.hm_noptif_icon)
    val notiType: TextView = itemView.findViewById(R.id.hm_notif_type)
    val notiOper: TextView = itemView.findViewById(R.id.hm_notif_oper)
    val notiTimestamp: TextView = itemView.findViewById(R.id.hm_notif_timestamp)

    fun bindData(model: NotifModel) {
        this.notiName.text = model.name
        this.notiType.text = model.type
        this.notiOper.text = " " + model.oper
        this.notiTimestamp.text = model.date

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
    }
}