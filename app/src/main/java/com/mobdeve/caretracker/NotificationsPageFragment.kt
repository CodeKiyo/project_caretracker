package com.mobdeve.caretracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.TestResultActivity.Companion.patientID


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

    private fun updateView() {
        // Update verticalRecyclerView
        val db = Firebase.firestore
        val userCollection = db.collection(MyFirestoreReferences.USERS_COLLECTION)
        val userNotifications = userCollection.document().collection("Notifications")
        val data = ArrayList<NotificationModel>()
        val userid = arguments?.getString(USER_ID).toString()

        userCollection
            .whereEqualTo("id", userid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result!!.documents) {
                    userCollection.document(document.id).collection("Notification")
                        .get()
                        .addOnSuccessListener { result ->
                            for(document in result!!.documents) {
                                println(document.id)
                                val newData = NotificationModel(
                                    document.get("date").toString(),
                                    document.get("name").toString(),
                                    document.get("oper").toString(),
                                    document.get("type").toString()
                                )
                                data.add(newData)
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