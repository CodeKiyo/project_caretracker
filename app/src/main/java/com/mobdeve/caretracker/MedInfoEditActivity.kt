package com.mobdeve.caretracker

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.caretracker.databinding.MedInfoEditPageBinding
import java.text.SimpleDateFormat
import java.util.Date

class MedInfoEditActivity : AppCompatActivity()  {
    private lateinit var binding : MedInfoEditPageBinding
    private lateinit var firestore: FirebaseFirestore
    private var recordId: String? = null
    private var patientId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = MedInfoEditPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // Ensure this is the correct layout

        firestore = FirebaseFirestore.getInstance()

        recordId = intent.getStringExtra("medinfoId")
        patientId = intent.getStringExtra(MedInfoActivity.PATIENT_ID)

        val weight = intent.getStringExtra(MyFirestoreReferences.PATIENTWEIGHT_FIELD).toString()
        val heartRate = intent.getStringExtra(MyFirestoreReferences.PATIENTHEARTRATE_FIELD).toString()
        val bloodPressure = intent.getStringExtra(MyFirestoreReferences.PATIENTBLOODPRESSURE_FIELD).toString()
        val bodyTemp = intent.getStringExtra(MyFirestoreReferences.PATIENTBODYTEMPERATURE_FIELD).toString()
        val respRate = intent.getStringExtra(MyFirestoreReferences.PATIENTRESPIRATIONRATE_FIELD).toString()

        val chiefComp = intent.getStringExtra(MyFirestoreReferences.PATIENTCHIEFCOMPLAINT_FIELD).toString()
        val objectives = intent.getStringExtra(MyFirestoreReferences.PATIENTOBJECTIVES_FIELD).toString()
        val diagnosis = intent.getStringExtra(MyFirestoreReferences.PATIENTDIAGNOSIS_FIELD).toString()
        val plan = intent.getStringExtra(MyFirestoreReferences.PATIENTPLAN_FIELD).toString()
        val comments = intent.getStringExtra(MyFirestoreReferences.PATIENTCOMMENTS_FIELD).toString()

        binding.editWeight.text = Editable.Factory.getInstance().newEditable(weight)
        binding.editHeartRate.text = Editable.Factory.getInstance().newEditable(heartRate)
        binding.editBloodPress.text = Editable.Factory.getInstance().newEditable(bloodPressure)
        binding.editBodyTemp.text = Editable.Factory.getInstance().newEditable(bodyTemp)
        binding.editRespRate.text = Editable.Factory.getInstance().newEditable(respRate)

        binding.editChiefComp.text = Editable.Factory.getInstance().newEditable(chiefComp)
        binding.editObject.text = Editable.Factory.getInstance().newEditable(objectives)
        binding.editDiagno.text = Editable.Factory.getInstance().newEditable(diagnosis)
        binding.editPlan.text = Editable.Factory.getInstance().newEditable(plan)
        binding.editComment.text = Editable.Factory.getInstance().newEditable(comments)

        binding.saveButt.setOnClickListener() {
            saveChanges()
        }
        binding.backbutton.setOnClickListener {
            finish()
        }

        val weightE = binding.editWeight
        val heartRateE = binding.editHeartRate
        val bloodPressE = binding.editBloodPress
        val bodyTempE = binding.editBodyTemp
        val respRateE = binding.editRespRate

        val chiefCompE = binding.editChiefComp
        val objectiveE = binding.editObject
        val diagnoE = binding.editDiagno
        val planE = binding.editPlan
        val commentE = binding.editComment

        val reqRecord = listOf(weightE, heartRateE, bloodPressE, bodyTempE, respRateE, chiefCompE, objectiveE, diagnoE, planE, commentE)

        reqRecord.forEach { editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    if (binding.saveButt.isEnabled == true)
                        binding.saveButt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#34AE7A")))
                    else
                        binding.saveButt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3A3A3A")))

                    val vitCount = listOf(weightE, heartRateE, bloodPressE, bodyTempE, respRateE).count { it.text.isNotEmpty() }
                    val recCount = listOf(chiefCompE, objectiveE, diagnoE, planE).count { it.text.isNotEmpty() }

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

                    val vitCount = listOf(weightE, heartRateE, bloodPressE, bodyTempE, respRateE).count { it.text.isNotEmpty() }
                    val recCount = listOf(chiefCompE, objectiveE, diagnoE, planE).count { it.text.isNotEmpty() }

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

                    val vitCount = listOf(weightE, heartRateE, bloodPressE, bodyTempE, respRateE).count { it.text.isNotEmpty() }
                    val recCount = listOf(chiefCompE, objectiveE, diagnoE, planE).count { it.text.isNotEmpty() }

                    if (recCount == 0 || recCount == 4)
                        if (vitCount == 0 && recCount == 0)
                            binding.saveButt.setEnabled(false)
                        else
                            binding.saveButt.setEnabled(true)
                }
            })
        }
    }

    private fun saveChanges() {
        val weight = binding.editWeight.text.toString().trim()
        val heartRate = binding.editHeartRate.text.toString().trim()
        val bloodPress = binding.editBloodPress.text.toString().trim()
        val bodyTemp = binding.editBodyTemp.text.toString().trim()
        val respRate = binding.editRespRate.text.toString().trim()

        val chiefComp = binding.editChiefComp.text.toString().trim()
        val objective = binding.editObject.text.toString().trim()
        val diagno = binding.editDiagno.text.toString().trim()
        val plan = binding.editPlan.text.toString().trim()
        val comment = binding.editComment.text.toString().trim()

        val medInfo = hashMapOf(
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
            .document(recordId!!)
            .set(medInfo)
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