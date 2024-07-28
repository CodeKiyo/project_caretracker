package com.mobdeve.caretracker

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.caretracker.databinding.PrescriptionUpdatePageBinding

class PrescriptionUpdate : AppCompatActivity() {
    private lateinit var binding : PrescriptionUpdatePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = PrescriptionUpdatePageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val medName = intent.getStringExtra("medName").toString()
        val dosage = intent.getStringExtra("dosage").toString()
        val sig = intent.getStringExtra("sig").toString()

        binding.editMedName.text = Editable.Factory.getInstance().newEditable(medName)
        binding.editDosage.text = Editable.Factory.getInstance().newEditable(dosage)
        binding.editSig.text = Editable.Factory.getInstance().newEditable(sig)
    }
}