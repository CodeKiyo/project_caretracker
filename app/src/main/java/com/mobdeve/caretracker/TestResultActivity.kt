package com.mobdeve.caretracker

import TestResultAdapter
import TestResultDataGenerator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.R

class TestResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_results_page) // Ensure this is the correct layout

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataGenerator = TestResultDataGenerator()
        val testResults = dataGenerator.generateSampleData()
        val adapter = TestResultAdapter(testResults)
        recyclerView.adapter = adapter
    }
}
