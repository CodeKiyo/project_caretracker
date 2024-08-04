package com.mobdeve.caretracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.TestResultActivity.Companion.patientID
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class NotificationsPageFragment : Fragment(R.layout.homemenu_notification_list_fragment) {
    companion object{
        const val TAG = "NotificationsPageFragment"
        private const val USER_ID = "USER_ID"
    }

    fun newInstance(username: String): NotificationsPageFragment {
        val args = Bundle()
        args.putString(USER_ID, username)
        return NotificationsPageFragment().apply {
            arguments = args
        }
    }

    private lateinit var notificationsPageRecycler: RecyclerView

    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homemenu_notification_list_fragment, container, false)

        val userid = arguments?.getString(USER_ID).toString()
        println(userid)

        //val usernameTextView = view.findViewById<TextView>(R.id.)

        //usernameTextView.text = username

        // Set up recyclerview layouts
        notificationsPageRecycler = view.findViewById(R.id.notifRecyclerView)
        notificationsPageRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        updateView()
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateView() {
        // Update verticalRecyclerView
        val db = Firebase.firestore
        val userCollection = db.collection(MyFirestoreReferences.USERS_COLLECTION)
        val userNotifications = userCollection.document().collection("Notifications")
        val data = ArrayList<NotificationModel>()
        val userid = arguments?.getString(USER_ID).toString()
        val date = ArrayList<String>()

        userCollection
            .whereEqualTo("id", userid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result!!.documents) {
                    userCollection.document(document.id).collection("Notification")
                        .get()
                        .addOnSuccessListener { result ->
                            if (result != null && !result.isEmpty) {
                                data.clear()
                                for (document in result!!.documents) {
                                    date.add(document.get("date").toString())
                                }
                            }

                            val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
                            val dateObjects = date.mapNotNull { dateString ->
                                try {
                                    dateFormat.parse(dateString)
                                } catch (e: ParseException) {
                                    null // Handle the exception or return a default value
                                }
                            }
                            val sortedDateObjects = dateObjects.sortedDescending()
                            val sortedDateStrings = sortedDateObjects.map { dateObject ->
                                dateFormat.format(dateObject)
                            }


                            for(element in sortedDateStrings) {
                                println(element)
                                for(document in result!!.documents) {
                                    if (document.get("date").toString() == element) {
                                        val newData = NotificationModel(
                                            document.get("date").toString(),
                                            document.get("name").toString(),
                                            document.get("oper").toString(),
                                            document.get("type").toString()
                                        )
                                        data.add(newData)
                                    }
                                }
                            }
                            notificationsPageRecycler.adapter = NotificationAdapter(data, "Franco Carino")
                            notificationsPageRecycler.adapter?.notifyDataSetChanged()
                            return@addOnSuccessListener
                        }.addOnFailureListener { exception ->
                            println("Error getting documents: $exception")
                        }
                }
            }.addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }
}