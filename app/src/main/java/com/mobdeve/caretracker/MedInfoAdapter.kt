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
        holder.bindData(this.medInfos[position])
    }
}