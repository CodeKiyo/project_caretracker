package com.mobdeve.caretracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NotifAdapter (private val notifs: List<NotifModel>, private val username: String) : RecyclerView.Adapter<NotifViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homemenu_notification_list_recycleritem, parent, false)
        return NotifViewHolder(view, username)
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        val notif = notifs[position]
        holder.bindData(notif)

        when (notif.type) {
            "Medical Information" -> holder.notiIcon.setImageResource(R.drawable.patient_medinfo_icon)
        }
    }

    override fun getItemCount(): Int {
        return notifs.size
    }
}