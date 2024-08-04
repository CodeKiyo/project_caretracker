package com.mobdeve.caretracker

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.MedInfoActivity.Companion.PATIENT_ID
import com.mobdeve.caretracker.databinding.MedInfoAddPageBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class MedInfoAddActivity : AppCompatActivity() {
    private lateinit var binding : MedInfoAddPageBinding
    private lateinit var firestore: FirebaseFirestore
    private var patientId: String? = null
    private var userId: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MedInfoAddPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        patientId = intent.getStringExtra(MedInfoActivity.PATIENT_ID)
        userId = intent.getStringExtra(MedInfoActivity.USER_ID)

        if (patientId == null) {
            Toast.makeText(this, "Patient ID is missing", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if patient ID is missing
            return
        }

        binding.saveButt.setOnClickListener {
            createHealthRec()
        }

        binding.backbutton.setOnClickListener {
            finish()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createHealthRec() {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        val date = currentDate.format(formatter)
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
                val db = Firebase.firestore

                val patientCollection = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
                val patientRef = patientCollection.document(intent.getStringExtra(PATIENT_ID).toString())

                patientRef.get()
                    .addOnSuccessListener { result ->
                        val firestore = FirebaseFirestore.getInstance()

                        val notInfo = hashMapOf(
                            "date" to SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(
                                Date()
                            ).toString(),
                            "type" to "Medical Information",
                            "name" to result.get("patientName").toString(),
                            "oper" to "Added"
                        )

                        firestore.collection("Users")
                            .document(userId!!)
                            .collection("Notification")
                            .add(notInfo)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Health Record added successfully", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, MedInfoActivity::class.java).apply {
                                    putExtra(MedInfoActivity.PATIENT_ID, patientId)
                                    putExtra(MedInfoActivity.USER_ID, userId)
                                    // Use flags to clear the back stack
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                }

                                startActivity(intent)
                                finish() // Close the activity
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    this,
                                    "Error adding health record: ${e.localizedMessage}",
                                    Toast.LENGTH_LONG
                                ).show()
                                e.printStackTrace()  // Log the stack trace for debugging
                            }
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding health record: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                e.printStackTrace()  // Log the stack trace for debugging
            }
    }
}