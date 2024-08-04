package com.mobdeve.caretracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeMenuPageFragment : Fragment(R.layout.homemenu_patient_list_fragment) {
    companion object{
        const val TAG = "HomeMenuFragment"
        private const val ARG_USERNAME = "username"
        private const val USER_ID = "USER_ID"

        fun newInstance(username: String, userId: String): HomeMenuPageFragment {
            val args = Bundle()

            args.putString(ARG_USERNAME, username)
            args.putString(USER_ID, userId)

            return HomeMenuPageFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var homemenuPatientListRecycler: RecyclerView

    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homemenu_patient_list_fragment, container, false)

        val username = arguments?.getString(ARG_USERNAME)

        val usernameTextView = view.findViewById<TextView>(R.id.homemenu_patient_list_username)

        usernameTextView.text = username

        // Set up recyclerview layouts
        homemenuPatientListRecycler = view.findViewById(R.id.patientlistRecyclerView)
        homemenuPatientListRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        // transition to userpage fragment
//        view.findViewById<ImageView>(R.id.user_dp).setOnClickListener {
//            val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.BottomNavigation)
//            bottomNav?.selectedItemId = R.id.bottom_user
//        }
        updateView()
        return view
    }

    private fun updateView() {
        val patientName = MyFirestoreReferences.PATIENTNAME_FIELD
        val patientAge = MyFirestoreReferences.PATIENTAGE_FIELD
        val patientRoom = MyFirestoreReferences.PATIENTROOM_FIELD
        val patientBirthdate = MyFirestoreReferences.PATIENTBIRTHDATE_FIELD
        val patientCivilStatus = MyFirestoreReferences.PATIENTCIVILSTATUS_FIELD
        val patientECN = MyFirestoreReferences.PATIENTECN_FIELD
        val patientECNo = MyFirestoreReferences.PATIENTECNO_FIELD
        val patientEmail = MyFirestoreReferences.PATIENTEMAIL_FIELD
        val patientMobileno = MyFirestoreReferences.PATIENTMOBILENO_FIELD
        val patientNationality = MyFirestoreReferences.PATIENTNATIONALITY_FIELD
        val patientReligion = MyFirestoreReferences.PATIENTRELIGION_FIELD
        val patientSex = MyFirestoreReferences.PATIENTSEX_FIELD

        // Update verticalRecyclerView
        val db = Firebase.firestore
        val patientsRef = db.collection(MyFirestoreReferences.PATIENT_COLLECTION)
        val usersRef = db.collection(MyFirestoreReferences.USERS_COLLECTION)
        val data = ArrayList<PatientModel>()

        patientsRef.get().addOnSuccessListener { result ->
            for (document in result!!.documents) {
                val newData = PatientModel(
                    document.get(patientName).toString(),
                    document.get(patientAge).toString().toInt(),
                    document.get(patientRoom).toString().toInt(),
                    document.get(patientBirthdate).toString(),
                    document.get(patientCivilStatus).toString(),
                    document.get(patientECN).toString(),
                    document.get(patientECNo).toString(),
                    document.get(patientEmail).toString(),
                    document.get(patientMobileno).toString(),
                    document.get(patientNationality).toString(),
                    document.get(patientReligion).toString(),
                    document.get(patientSex).toString())
                data.add(newData)
            }
            homemenuPatientListRecycler.adapter = PatientAdapter(data, arguments?.getString(ARG_USERNAME).toString(), arguments?.getString(USER_ID).toString())
            homemenuPatientListRecycler.adapter?.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }
    }

}