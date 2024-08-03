package com.mobdeve.caretracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.HomeMenuPageFragment.Companion
import com.mobdeve.caretracker.databinding.HomemenuNotificationListFragmentBinding
import com.mobdeve.caretracker.databinding.HomemenuSettingsFragmentBinding
import javax.annotation.Nullable


class HomeMenuNotifFragment : Fragment(R.layout.homemenu_notification_list_fragment) {
    companion object{
        const val TAG = "HomeMenuFragment"
        private const val ARG_USERNAME = "username"
        private const val USER_ID = "USER_ID"
    }

    fun newInstance(username: String, userId: String): HomeMenuNotifFragment {
        val args = Bundle()

        args.putString(ARG_USERNAME, username)
        args.putString(USER_ID, userId)

        return HomeMenuNotifFragment().apply {
            arguments = args
        }
    }

    private lateinit var homemenuNotifListRecycler: RecyclerView
    private lateinit var notifPage: HomemenuNotificationListFragmentBinding

    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        notifPage = HomemenuNotificationListFragmentBinding.inflate(layoutInflater)
        activity?.setContentView(notifPage.root)

        val username = arguments?.getString(ARG_USERNAME)

        // Set up recyclerview layouts
        homemenuNotifListRecycler = notifPage.notifRecyclerView
        homemenuNotifListRecycler.layoutManager = LinearLayoutManager(requireContext(),
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
        val notiName = "name"
        val notiType = "type"
        val notiOper = "oper"
        val notiTime = "date"

        // Update verticalRecyclerView
        val db = Firebase.firestore
        val usersRef = db.collection(MyFirestoreReferences.USERS_COLLECTION)
        val data = ArrayList<NotifModel>()

        usersRef
            .document(arguments?.getString(USER_ID).toString())
            .collection("Notification")
            .get()
            .addOnSuccessListener { result ->
                for (document in result!!.documents) {
                    val newData = NotifModel(
                        document.get(notiTime).toString(),
                        document.get(notiName).toString(),
                        document.get(notiOper).toString(),
                        document.get(notiType).toString()
                    )

                    data.add(newData)
                    System.out.println("test")
                }
                homemenuNotifListRecycler.adapter = NotifAdapter(data, arguments?.getString(ARG_USERNAME).toString())
                homemenuNotifListRecycler.adapter?.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }
    }

}