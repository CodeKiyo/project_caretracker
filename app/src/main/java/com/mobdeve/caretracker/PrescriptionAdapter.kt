package com.mobdeve.caretracker

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PrescriptionAdapter (private val prescriptions: List<PrescriptionModel>): RecyclerView.Adapter<PrescriptionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prescription_recycleritem, parent, false)
        return PrescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        val prescript = prescriptions[position]
        holder.medName.text = prescript.medName
        holder.dosage.text = prescript.dosage
        holder.sig.text = prescript.sig

        holder.editButt.setOnClickListener(){
            val intent = Intent(holder.editButt.context, PrescriptionUpdate::class.java)
            intent.putExtra("medName", prescript.medName)
            intent.putExtra("dosage", prescript.dosage)
            intent.putExtra("sig", prescript.sig)

            holder.editButt.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return prescriptions.size
    }
}