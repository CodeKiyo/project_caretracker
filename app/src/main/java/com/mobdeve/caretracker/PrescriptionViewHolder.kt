package com.mobdeve.caretracker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PrescriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val medName: TextView = itemView.findViewById(R.id.med_name)
    val dosage: TextView = itemView.findViewById(R.id.dosage)
    val sig: TextView = itemView.findViewById(R.id.sig)

    fun bindData(prescriptionModel: PrescriptionModel) {
        this.medName.text = prescriptionModel.medName
        this.dosage.text = prescriptionModel.dosage
        this.sig.text = prescriptionModel.sig
    }
}
