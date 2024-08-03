package com.mobdeve.caretracker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MedInfoAdapter (private val medInfos: List<MedInfoModel>, private val patientId: String, private val onDeleteClick: (String, String) -> Unit, private val userId: String) : RecyclerView.Adapter<MedInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.med_info_recycleritem, parent, false)
        return MedInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medInfos.size;
    }

    override fun onBindViewHolder(holder: MedInfoViewHolder, position: Int) {
        val medInfo = medInfos[position]
        holder.bindData(medInfo)

        if (medInfo.patientWeight == "")
            holder.weightLL.visibility = View.GONE

        if (medInfo.patientHeartRate == "")
            holder.heartRateLL.visibility = View.GONE

        if (medInfo.patientBloodPressure == "")
            holder.bloodPressureLL.visibility = View.GONE

        if (medInfo.patientBodyTemperature == "")
            holder.bodyTempLL.visibility = View.GONE

        if (medInfo.patientRespirationRate == "")
            holder.respRateLL.visibility = View.GONE

        if (medInfo.patientChiefComplaint == "")
            holder.chiefCompLL.visibility = View.GONE

        if (medInfo.patientObjectives == "")
            holder.objLL.visibility = View.GONE

        if (medInfo.patientDiagnosis == "")
            holder.diagLL.visibility = View.GONE

        if (medInfo.patientPlan == "")
            holder.planLL.visibility = View.GONE

        if (medInfo.patientComments == "")
            holder.commLL.visibility = View.GONE

        // Edit Button Functionality
        holder.editButt.setOnClickListener() {
            val intent = Intent(holder.editButt.context, MedInfoEditActivity::class.java).apply {
                putExtra("medinfoId", medInfo.id)
                putExtra(MyFirestoreReferences.PATIENTWEIGHT_FIELD, medInfo.patientWeight)
                putExtra(MyFirestoreReferences.PATIENTHEARTRATE_FIELD, medInfo.patientHeartRate)
                putExtra(MyFirestoreReferences.PATIENTBLOODPRESSURE_FIELD, medInfo.patientBloodPressure)
                putExtra(MyFirestoreReferences.PATIENTBODYTEMPERATURE_FIELD, medInfo.patientBodyTemperature)
                putExtra(MyFirestoreReferences.PATIENTRESPIRATIONRATE_FIELD, medInfo.patientRespirationRate)
                putExtra(MyFirestoreReferences.PATIENTCHIEFCOMPLAINT_FIELD, medInfo.patientChiefComplaint)
                putExtra(MyFirestoreReferences.PATIENTOBJECTIVES_FIELD, medInfo.patientObjectives)
                putExtra(MyFirestoreReferences.PATIENTDIAGNOSIS_FIELD, medInfo.patientDiagnosis)
                putExtra(MyFirestoreReferences.PATIENTPLAN_FIELD, medInfo.patientPlan)
                putExtra(MyFirestoreReferences.PATIENTCOMMENTS_FIELD, medInfo.patientComments)
                putExtra("PATIENT_ID", patientId)
                putExtra("USER_ID", userId)
            }
            holder.editButt.context.startActivity(intent)
        }

        holder.delButt.setOnClickListener() {
            onDeleteClick(medInfo.id, userId)
        }
    }
}