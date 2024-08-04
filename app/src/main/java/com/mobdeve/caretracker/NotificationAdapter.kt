package com.mobdeve.caretracker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.PrescriptionAdapter.PrescriptionViewHolder
import com.mobdeve.caretracker.databinding.PrescriptionRecycleritemBinding

class NotificationAdapter(private val data: ArrayList<NotificationModel>, private val USER_ID: String) : RecyclerView.Adapter<NotificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.homemenu_notification_list_recycleritem, parent, false)
        return NotificationViewHolder(view, USER_ID)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindData(this.data[position])
    }
    override fun getItemCount(): Int {
        return this.data.size
    }
}