package com.mobdeve.caretracker

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedInfoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val heartRate: TextView = itemView.findViewById(R.id.heart_rate)
    val bloodPressure: TextView = itemView.findViewById(R.id.blood_pressure)
    val bodyTemp: TextView = itemView.findViewById(R.id.body_temp)
    val respRate: TextView = itemView.findViewById(R.id.resp_rate)

    val chiefComp: TextView = itemView.findViewById(R.id.chief_comp)
    val objectives: TextView = itemView.findViewById(R.id.objective)
    val diagnosis: TextView = itemView.findViewById(R.id.diagnosis)
    val plan: TextView = itemView.findViewById(R.id.plan)
    val comments: TextView = itemView.findViewById(R.id.comment)

    val heartRateLL: LinearLayout = itemView.findViewById(R.id.heartRateLL)
    val bloodPressureLL: LinearLayout = itemView.findViewById(R.id.bloodPressureLL)
    val bodyTempLL: LinearLayout = itemView.findViewById(R.id.bodyTempLL)
    val respRateLL: LinearLayout = itemView.findViewById(R.id.respRateLL)
    val chiefCompLL: LinearLayout = itemView.findViewById(R.id.chiefCompLL)
    val objLL: LinearLayout = itemView.findViewById(R.id.objLL)
    val diagLL: LinearLayout = itemView.findViewById(R.id.diagLL)
    val planLL: LinearLayout = itemView.findViewById(R.id.planLL)
    val commLL: LinearLayout = itemView.findViewById(R.id.commLL)

    val editButt: ImageView = itemView.findViewById(R.id.edit_butt)
    val deleteButt: ImageView = itemView.findViewById(R.id.delete_butt)
}