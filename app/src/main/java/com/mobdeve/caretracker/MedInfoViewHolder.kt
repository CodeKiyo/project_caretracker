package com.mobdeve.caretracker

import android.media.Image
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MedInfoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val patientDate: TextView = itemView.findViewById(R.id.date)
    val patientBloodPressure: TextView = itemView.findViewById(R.id.blood_pressure)
    val patientBodyTemperature: TextView = itemView.findViewById(R.id.body_temp)
    val patientChiefComplaint: TextView = itemView.findViewById(R.id.chief_comp)
    val patientComments: TextView = itemView.findViewById(R.id.comment)
    val patientDiagnosis: TextView = itemView.findViewById(R.id.diagnosis)
    val patientHeartRate: TextView = itemView.findViewById(R.id.heart_rate)
    val patientObjectives: TextView = itemView.findViewById(R.id.objective)
    val patientPlan: TextView = itemView.findViewById(R.id.plan)
    val patientRespirationRate: TextView = itemView.findViewById(R.id.resp_rate)
    val patientWeight: TextView = itemView.findViewById(R.id.weight)
    val editButt: ImageView = itemView.findViewById(R.id.edit_butt)
    val delButt: ImageView = itemView.findViewById(R.id.delete_butt)

    val weightLL: LinearLayout = itemView.findViewById(R.id.weightLL)
    val heartRateLL: LinearLayout = itemView.findViewById(R.id.heartRateLL)
    val bloodPressureLL: LinearLayout = itemView.findViewById(R.id.bloodPressureLL)
    val bodyTempLL: LinearLayout = itemView.findViewById(R.id.bodyTempLL)
    val respRateLL: LinearLayout = itemView.findViewById(R.id.respRateLL)
    val chiefCompLL: LinearLayout = itemView.findViewById(R.id.chiefCompLL)
    val objLL: LinearLayout = itemView.findViewById(R.id.objLL)
    val diagLL: LinearLayout = itemView.findViewById(R.id.diagLL)
    val planLL: LinearLayout = itemView.findViewById(R.id.planLL)
    val commLL: LinearLayout = itemView.findViewById(R.id.commLL)

    fun bindData(model: MedInfoModel) {
        this.patientDate.text = model.patientDate
        this.patientBloodPressure.text = model.patientBloodPressure
        this.patientBodyTemperature.text = model.patientBodyTemperature + " °C"
        this.patientChiefComplaint.text = model.patientChiefComplaint
        this.patientComments.text = model.patientComments
        this.patientDiagnosis.text = model.patientDiagnosis
        this.patientHeartRate.text = model.patientHeartRate + " BPM"
        this.patientObjectives.text = model.patientObjectives
        this.patientPlan.text = model.patientPlan
        this.patientRespirationRate.text = model.patientRespirationRate + " BPM"
        this.patientWeight.text = model.patientWeight + " kg"
    }
}