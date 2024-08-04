package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.PatientViewActivity.Companion.patientName
import com.mobdeve.caretracker.databinding.HomemenuPageBinding

class HomeMenuPageActivity : AppCompatActivity()  {
    companion object {
        const val userID: String = "USER_ID"
    }

    private lateinit var mainmenuPage: HomemenuPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainmenuPage = HomemenuPageBinding.inflate(layoutInflater)
        setContentView(mainmenuPage.root)



        val db = Firebase.firestore
        val usersRef = db.collection(MyFirestoreReferences.USERS_COLLECTION)
        var userId = ""

        usersRef
            .whereEqualTo(MyFirestoreReferences.NAME_FIELD, "Franco Carino")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    userId = document.id
                    replaceFragment(HomeMenuPageFragment.newInstance("Franco Carino", userId))
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }

        // The Code for Bottom Navigation
        val bottomNavigationView = mainmenuPage.BottomNavigation

        val id = intent.getStringExtra(userID).toString()

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_todo -> {
                    // start activity
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    replaceFragment(HomeMenuPageFragment.newInstance("Franco Carino", userId))
                    // finish
                    true
                }
                R.id.bottom_notifications -> {
                    // start activity
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    replaceFragment(NotificationsPageFragment().newInstance(id))
                    // finish
                    true
                }
                R.id.bottom_settings -> {
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    replaceFragment(SettingsPageFragment().newInstance("Franco Carino"))
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()
    }
}