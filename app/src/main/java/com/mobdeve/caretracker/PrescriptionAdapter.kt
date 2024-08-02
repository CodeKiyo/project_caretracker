package com.mobdeve.caretracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.databinding.PrescriptionRecycleritemBinding

class PrescriptionAdapter(
    private val prescriptions: List<PrescriptionModel>,
    private val onDeleteClick: (String) -> Unit,
    private val onEditClick: (PrescriptionModel) -> Unit // Add this callback
) : RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val binding = PrescriptionRecycleritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrescriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        val prescription = prescriptions[position]
        holder.bind(prescription)
        holder.binding.editButt.setOnClickListener {
            onEditClick(prescription) // Notify activity about edit action
        }
        holder.binding.deleteButt.setOnClickListener {
            onDeleteClick(prescription.id)
        }
    }

    override fun getItemCount(): Int = prescriptions.size

    class PrescriptionViewHolder(val binding: PrescriptionRecycleritemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(prescription: PrescriptionModel) {
            binding.medName.text = prescription.medName
            binding.dosage.text = prescription.dosage
            binding.sig.text = prescription.sig
        }
    }
}
