package com.mobdeve.caretracker

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MedInfoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val patientBloodPressure: TextView = itemView.findViewById(R.id.weight)
    val patientBodyTemperature: TextView = itemView.findViewById(R.id.body_temp)
    val patientChiefComplaint: TextView = itemView.findViewById(R.id.chief_comp)
    val patientComments: TextView = itemView.findViewById(R.id.comment)
    val patientDiagnosis: TextView = itemView.findViewById(R.id.diagnosis)
    val patientHeartRate: TextView = itemView.findViewById(R.id.heart_rate)
    val patientObjectives: TextView = itemView.findViewById(R.id.objective)
    val patientPlan: TextView = itemView.findViewById(R.id.plan)
    val patientRespirationRate: TextView = itemView.findViewById(R.id.resp_rate)
    val patientWeight: TextView = itemView.findViewById(R.id.weight)

    fun bindData(model: MedInfoModel) {
        this.patientBloodPressure.text = model.patientBloodPressure
        this.patientBodyTemperature.text = model.patientBodyTemperature
        this.patientChiefComplaint.text = model.patientChiefComplaint
        this.patientComments.text = model.patientComments
        this.patientDiagnosis.text = model.patientDiagnosis
        this.patientHeartRate.text = model.patientHeartRate
        this.patientObjectives.text = model.patientObjectives
        this.patientPlan.text = model.patientPlan
        this.patientRespirationRate.text = model.patientRespirationRate
        this.patientWeight.text = model.patientWeight.toString()
    }
}