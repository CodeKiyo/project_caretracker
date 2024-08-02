package com.mobdeve.caretracker

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.caretracker.databinding.MedInfoAddPageBinding
import java.text.SimpleDateFormat
import java.util.Date

class MedInfoAddActivity : AppCompatActivity() {
    private lateinit var binding : MedInfoAddPageBinding
    private lateinit var firestore: FirebaseFirestore
    private var patientId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = MedInfoAddPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        patientId = intent.getStringExtra(MedInfoActivity.PATIENT_ID)

        if (patientId == null) {
            Toast.makeText(this, "Patient ID is missing", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if patient ID is missing
            return
        }

        binding.saveButt.setOnClickListener {
            createHealthRec()
        }

        val weight = binding.addWeight
        val heartRate = binding.addHeartRate
        val bloodPress = binding.addBloodPress
        val bodyTemp = binding.addBodyTemp
        val respRate = binding.addRespRate

        val chiefComp = binding.addChiefComp
        val objective = binding.addObject
        val diagno = binding.addDiagno
        val plan = binding.addPlan
        val comment = binding.addComment

        val reqRecord = listOf(weight, heartRate, bloodPress, bodyTemp, respRate, chiefComp, objective, diagno, plan, comment)

        reqRecord.forEach { editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    if (binding.saveButt.isEnabled == true)
                        binding.saveButt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#34AE7A")))
                    else
                        binding.saveButt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3A3A3A")))

                    val vitCount = listOf(weight, heartRate, bloodPress, bodyTemp, respRate).count { it.text.isNotEmpty() }
                    val recCount = listOf(chiefComp, objective, diagno, plan).count { it.text.isNotEmpty() }

                    if (recCount == 0 || recCount == 4)
                        if (vitCount == 0 && recCount == 0)
                            binding.saveButt.setEnabled(false)
                        else
                            binding.saveButt.setEnabled(true)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (binding.saveButt.isEnabled == true)
                        binding.saveButt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#34AE7A")))
                    else
                        binding.saveButt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3A3A3A")))

                    val vitCount = listOf(weight, heartRate, bloodPress, bodyTemp, respRate).count { it.text.isNotEmpty() }
                    val recCount = listOf(chiefComp, objective, diagno, plan).count { it.text.isNotEmpty() }

                    if (recCount == 0 || recCount == 4)
                        if (vitCount == 0 && recCount == 0)
                            binding.saveButt.setEnabled(false)
                        else
                            binding.saveButt.setEnabled(true)
                }

                override fun afterTextChanged(s: Editable?) {
                    if (binding.saveButt.isEnabled == true)
                        binding.saveButt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#34AE7A")))
                    else
                        binding.saveButt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3A3A3A")))

                    val vitCount = listOf(weight, heartRate, bloodPress, bodyTemp, respRate).count { it.text.isNotEmpty() }
                    val recCount = listOf(chiefComp, objective, diagno, plan).count { it.text.isNotEmpty() }

                    if (recCount == 0 || recCount == 4)
                        if (vitCount == 0 && recCount == 0)
                            binding.saveButt.setEnabled(false)
                        else
                            binding.saveButt.setEnabled(true)
                }
            })
        }
    }

    private fun createHealthRec() {
        val date = SimpleDateFormat("MM/dd/yyyy").format(Date()).toString()
        val weight = binding.addWeight.text.toString().trim()
        val heartRate = binding.addHeartRate.text.toString().trim()
        val bloodPress = binding.addBloodPress.text.toString().trim()
        val bodyTemp = binding.addBodyTemp.text.toString().trim()
        val respRate = binding.addRespRate.text.toString().trim()

        val chiefComp = binding.addChiefComp.text.toString().trim()
        val objective = binding.addObject.text.toString().trim()
        val diagno = binding.addDiagno.text.toString().trim()
        val plan = binding.addPlan.text.toString().trim()
        val comment = binding.addComment.text.toString().trim()

        val medInfo = hashMapOf(
            "patientDate" to date,
            "patientWeight" to weight,
            "patientHeartRate" to heartRate,
            "patientBloodPressure" to bloodPress,
            "patientBodyTemperature" to bodyTemp,
            "patientRespirationRate" to respRate,

            "patientChiefComplaint" to chiefComp,
            "patientObjectives" to objective,
            "patientDiagnosis" to diagno,
            "patientPlan" to plan,
            "patientComments" to comment,
        )

        // Save data to Firestore in the patient's "Prescription" sub-collection
        firestore.collection("Patients")
            .document(patientId!!)
            .collection("Medical Information")
            .add(medInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "Health Record added successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MedInfoActivity::class.java).apply {
                    putExtra(MedInfoActivity.PATIENT_ID, patientId)
                    // Use flags to clear the back stack
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                startActivity(intent)
                finish() // Close the activity
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding health record: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                e.printStackTrace()  // Log the stack trace for debugging
            }
    }
}