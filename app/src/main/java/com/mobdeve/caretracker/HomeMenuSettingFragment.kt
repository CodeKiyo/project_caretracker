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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.HomeMenuNotifFragment.Companion
import com.mobdeve.caretracker.databinding.HomemenuSettingsFragmentBinding
import javax.annotation.Nullable


class HomeMenuSettingFragment : Fragment(R.layout.homemenu_settings_fragment) {
    companion object{
        const val TAG = "HomeMenuFragment"
        private const val ARG_USERNAME = "username"
        private const val USER_ID = "USER_ID"
    }

    fun newInstance(username: String, userId: String): HomeMenuSettingFragment {
        val args = Bundle()

        args.putString(HomeMenuSettingFragment.ARG_USERNAME, username)
        args.putString(USER_ID, userId)

        return HomeMenuSettingFragment().apply {
            arguments = args
        }
    }

    private lateinit var settingPage: HomemenuSettingsFragmentBinding

    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        settingPage = HomemenuSettingsFragmentBinding.inflate(layoutInflater)
        activity?.setContentView(settingPage.root)

        val username = arguments?.getString(ARG_USERNAME)

        // Set up recyclerview layouts


        // transition to userpage fragment
//        view.findViewById<ImageView>(R.id.user_dp).setOnClickListener {
//            val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.BottomNavigation)
//            bottomNav?.selectedItemId = R.id.bottom_user
//        }

        settingPage.logOut.setOnClickListener() {
            val intent = Intent(settingPage.logOut.context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        updateView()
        return view
    }

    private fun updateView() {

    }

}