package com.mobdeve.caretracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.mobdeve.caretracker.databinding.PrescriptionAddPageBinding

class PrescriptionAdd : AppCompatActivity() {
    private lateinit var binding : PrescriptionAddPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = PrescriptionAddPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}