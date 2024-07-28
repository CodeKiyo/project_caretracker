package com.mobdeve.caretracker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MedInfoAdapter (private val medInfos: List<MedInfoModel>) : RecyclerView.Adapter<MedInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.med_info_recycleritem, parent, false)
        return MedInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medInfos.size;
    }

    override fun onBindViewHolder(holder: MedInfoViewHolder, position: Int) {
        val medInfo = medInfos[position]

        holder.date.text = medInfo.date

        holder.weight.text = medInfo.weight.toString()
        holder.heartRate.text = medInfo.heartRate.toString()
        holder.bloodPressure.text = medInfo.bloodPressure
        holder.bodyTemp.text = medInfo.bodyTemp.toString()
        holder.respRate.text = medInfo.respRate.toString()

        holder.chiefComp.text = medInfo.chiefComp
        holder.objectives.text = medInfo.objectives
        holder.diagnosis.text = medInfo.diagnosis
        holder.plan.text = medInfo.plan
        holder.comments.text = medInfo.comments

        if (medInfo.weight == 0f)
            holder.weightLL.visibility = View.GONE

        if (medInfo.heartRate == 0)
            holder.heartRateLL.visibility = View.GONE

        if (medInfo.bloodPressure == "")
            holder.bloodPressureLL.visibility = View.GONE

        if (medInfo.bodyTemp == 0f)
            holder.bodyTempLL.visibility = View.GONE

        if (medInfo.respRate == 0)
            holder.respRateLL.visibility = View.GONE

        if (medInfo.chiefComp == "")
            holder.chiefCompLL.visibility = View.GONE

        if (medInfo.objectives == "")
            holder.objLL.visibility = View.GONE

        if (medInfo.diagnosis == "")
            holder.diagLL.visibility = View.GONE

        if (medInfo.plan == "")
            holder.planLL.visibility = View.GONE

        if (medInfo.comments == "")
            holder.commLL.visibility = View.GONE

        holder.editButt.setOnClickListener() {
            val intent = Intent(holder.editButt.context, MedInfoEditActivity::class.java)

            intent.putExtra("weight", medInfo.weight)
            intent.putExtra("heartRate", medInfo.heartRate)
            intent.putExtra("bloodPres", medInfo.bloodPressure)
            intent.putExtra("bodyTemp", medInfo.bodyTemp)
            intent.putExtra("respRate", medInfo.respRate)

            intent.putExtra("chiefComp", medInfo.chiefComp)
            intent.putExtra("objective", medInfo.objectives)
            intent.putExtra("diagnosis", medInfo.diagnosis)
            intent.putExtra("plan", medInfo.plan)
            intent.putExtra("comment", medInfo.comments)

            holder.editButt.context.startActivity(intent)
        }
    }
}