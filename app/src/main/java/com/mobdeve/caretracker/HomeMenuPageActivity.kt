package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mobdeve.caretracker.databinding.HomemenuPageBinding

class HomeMenuPageActivity : AppCompatActivity()  {
    private lateinit var mainmenuPage: HomemenuPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainmenuPage = HomemenuPageBinding.inflate(layoutInflater)
        setContentView(mainmenuPage.root)
        replaceFragment(HomeMenuPageFragment.newInstance("Franco Carino"))

        // The Code for Bottom Navigation
        val bottomNavigationView = mainmenuPage.BottomNavigation

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_todo -> {
                    // start activity
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    replaceFragment(HomeMenuPageFragment.newInstance("Franco Carino"))
                    // finish
                    true
                }
                R.id.bottom_notifications -> {
                    // start activity
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    //replaceFragment(HomeMenuPageFragment.newInstance(username))
                    // finish
                    true
                }
//                R.id.bottom_settings -> {
//
//                }
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