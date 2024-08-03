package com.mobdeve.caretracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class NotificationsPageFragment : Fragment(R.layout.homemenu_notification_list_fragment) {
    companion object{
        const val TAG = "NotificationsPageFragment"
        private const val USER_ID = "USER_ID"

        fun newInstance(username: String): HomeMenuPageFragment {
            val args = Bundle()
            args.putString(USER_ID, username)
            return HomeMenuPageFragment().apply {
                arguments = args
            }
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

        val username = arguments?.getString(USER_ID)

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

    }

}