package com.mobdeve.caretracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PatientAdapter(private val data: ArrayList<PatientModel>, private val username: String) : RecyclerView.Adapter<PatientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.homemenu_patient_list_recycleritem, parent, false)
        return PatientViewHolder(view, username)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.bindData(this.data[position])
    }
    override fun getItemCount(): Int {
        return this.data.size
    }
}