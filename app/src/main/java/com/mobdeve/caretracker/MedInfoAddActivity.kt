package com.mobdeve.caretracker

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.caretracker.databinding.MedInfoAddPageBinding

class MedInfoAddActivity : AppCompatActivity() {
    private lateinit var binding : MedInfoAddPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = MedInfoAddPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}