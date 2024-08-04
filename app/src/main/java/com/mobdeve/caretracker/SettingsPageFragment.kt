package com.mobdeve.caretracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SettingsPageFragment : Fragment(R.layout.homemenu_settings_fragment) {
    companion object{
        const val TAG = "SettingsPageFragment"
        private const val USER_ID = "USER_ID"
    }

    fun newInstance(username: String): SettingsPageFragment {
        val args = Bundle()
        args.putString(USER_ID, username)
        return SettingsPageFragment().apply {
            arguments = args
        }
    }

    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homemenu_settings_fragment, container, false)

        val username = arguments?.getString(USER_ID)

        //val usernameTextView = view.findViewById<TextView>(R.id.)

        //usernameTextView.text = username

        // Set up recyclerview layouts
        val logoutButton = view.findViewById<TextView>(R.id.logout_button)
        logoutButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure you want to log out?")
            builder.setCancelable(true)
            builder.setPositiveButton("Yes") { dialog, id ->
                val intentToLoginPage = Intent(context, MainActivity::class.java)
                startActivity(intentToLoginPage)
                requireActivity().finish()
            }
            builder.setNegativeButton("No") { dialog, id -> dialog.cancel() }

            val alert = builder.create()
            alert.show()
        }

        updateView()
        return view
    }

    private fun updateView() {

    }

}