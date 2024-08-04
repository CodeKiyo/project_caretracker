package com.mobdeve.caretracker

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotificationViewHolder(itemView: View, private val username: String): RecyclerView.ViewHolder(itemView) {
    private val date: TextView = itemView.findViewById(R.id.hm_notif_timestamp)
    private val name: TextView = itemView.findViewById(R.id.hm_patient_name)
    private val type: TextView = itemView.findViewById(R.id.hm_notif_type)
    private val oper: TextView = itemView.findViewById(R.id.hm_notif_oper)

    fun bindData(model: NotificationModel) {
        this.name.text = model.name
        this.date.text = model.date
        this.type.text = model.type + " "
        this.oper.text = model.oper
    }
}