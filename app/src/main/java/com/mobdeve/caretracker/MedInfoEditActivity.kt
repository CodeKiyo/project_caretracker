package com.mobdeve.caretracker

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.caretracker.databinding.MedInfoEditPageBinding

class MedInfoEditActivity : AppCompatActivity()  {
    private lateinit var binding : MedInfoEditPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = MedInfoEditPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // Ensure this is the correct layout

        val heartRate = intent.getIntExtra("heartRate", 0).toString()
        val bloodPressure = intent.getFloatExtra("bloodPres", 0f).toString()
        val bodyTemp = intent.getFloatExtra("bodyTemp", 0f).toString()
        val respRate = intent.getFloatExtra("respRate", 0f).toString()

        val chiefComp = intent.getStringExtra("chiefComp").toString()
        val objectives = intent.getStringExtra("objective").toString()
        val diagnosis = intent.getStringExtra("diagnosis").toString()
        val plan = intent.getStringExtra("plan").toString()
        val comments = intent.getStringExtra("comment").toString()

        binding.editHeartRate.text = Editable.Factory.getInstance().newEditable(heartRate)
        binding.editBloodPress.text = Editable.Factory.getInstance().newEditable(bloodPressure)
        binding.editBodyTemp.text = Editable.Factory.getInstance().newEditable(bodyTemp)
        binding.editRespRate.text = Editable.Factory.getInstance().newEditable(respRate)

        binding.editChiefComp.text = Editable.Factory.getInstance().newEditable(chiefComp)
        binding.editObject.text = Editable.Factory.getInstance().newEditable(objectives)
        binding.editDiagno.text = Editable.Factory.getInstance().newEditable(diagnosis)
        binding.editPlan.text = Editable.Factory.getInstance().newEditable(plan)
        binding.editComment.text = Editable.Factory.getInstance().newEditable(comments)
    }
}