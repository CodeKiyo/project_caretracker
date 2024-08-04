package com.mobdeve.caretracker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.databinding.MedInfoRecycleritemBinding

class MedInfoAdapter(
    private val medInfos: List<MedInfoModel>,
    private val patientId: String,
    private val onDeleteClick: (String, String) -> Unit,
    private val userId: String
) : RecyclerView.Adapter<MedInfoAdapter.MedInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedInfoViewHolder {
        val binding = MedInfoRecycleritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = medInfos.size

    override fun onBindViewHolder(holder: MedInfoViewHolder, position: Int) {
        val medInfo = medInfos[position]
        holder.bind(medInfo, position)

        // Visibility settings
        holder.binding.apply {
            weightLL.visibility = if (medInfo.patientWeight.isEmpty()) View.GONE else View.VISIBLE
            heartRateLL.visibility = if (medInfo.patientHeartRate.isEmpty()) View.GONE else View.VISIBLE
            bloodPressureLL.visibility = if (medInfo.patientBloodPressure.isEmpty()) View.GONE else View.VISIBLE
            bodyTempLL.visibility = if (medInfo.patientBodyTemperature.isEmpty()) View.GONE else View.VISIBLE
            respRateLL.visibility = if (medInfo.patientRespirationRate.isEmpty()) View.GONE else View.VISIBLE
            chiefCompLL.visibility = if (medInfo.patientChiefComplaint.isEmpty()) View.GONE else View.VISIBLE
            objLL.visibility = if (medInfo.patientObjectives.isEmpty()) View.GONE else View.VISIBLE
            diagLL.visibility = if (medInfo.patientDiagnosis.isEmpty()) View.GONE else View.VISIBLE
            planLL.visibility = if (medInfo.patientPlan.isEmpty()) View.GONE else View.VISIBLE
            commLL.visibility = if (medInfo.patientComments.isEmpty()) View.GONE else View.VISIBLE
        }

        // Edit Button Functionality
        holder.binding.editButt.setOnClickListener {
            val intent = Intent(holder.itemView.context, MedInfoEditActivity::class.java).apply {
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
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.deleteButt.setOnClickListener {
            onDeleteClick(medInfo.id, userId)
        }
    }

    class MedInfoViewHolder(val binding: MedInfoRecycleritemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val recordNo: TextView = itemView.findViewById(R.id.record_counter)

        fun bind(medInfo: MedInfoModel, position: Int) {
            recordNo.text = "Record #${position + 1}"
            binding.apply {
                date.text = medInfo.patientDate
                weight.text = medInfo.patientWeight
                heartRate.text = medInfo.patientHeartRate
                bloodPressure.text = medInfo.patientBloodPressure
                bodyTemp.text = medInfo.patientBodyTemperature
                respRate.text = medInfo.patientRespirationRate
                chiefComp.text = medInfo.patientChiefComplaint
                objective.text = medInfo.patientObjectives
                diagnosis.text = medInfo.patientDiagnosis
                plan.text = medInfo.patientPlan
                comment.text = medInfo.patientComments
            }
        }
    }
}
