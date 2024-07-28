package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.databinding.PrescriptionPageBinding


class PrescriptionActivity : AppCompatActivity(){
    private lateinit var binding : PrescriptionPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = PrescriptionPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // Ensure this is the correct layout

        val recyclerView: RecyclerView = findViewById(R.id.prescription_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataGenerator = PrescriptionDataGenerator()
        val prescriptions = dataGenerator.generateSampleData()
        val adapter = PrescriptionAdapter(prescriptions)
        recyclerView.adapter = adapter

        binding.addButton.setOnClickListener() {
            val intent = Intent(this, PrescriptionAdd::class.java)

            startActivity(intent)
        }
    }
}