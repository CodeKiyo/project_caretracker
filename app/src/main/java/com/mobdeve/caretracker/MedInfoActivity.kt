package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.databinding.MedInfoEditPageBinding
import com.mobdeve.caretracker.databinding.MedInfoPageBinding

class MedInfoActivity : AppCompatActivity() {
    private lateinit var binding : MedInfoPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = MedInfoPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // Ensure this is the correct layout

        val recyclerView: RecyclerView = findViewById(R.id.medical_info_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataGenerator = MedInfoDataGenerator()
        val testResults = dataGenerator.generateSampleData()
        val adapter = MedInfoAdapter(testResults)
        recyclerView.adapter = adapter

        binding.addButt.setOnClickListener() {
            val intent = Intent(this, MedInfoAddActivity::class.java)

            startActivity(intent)
        }
    }
}