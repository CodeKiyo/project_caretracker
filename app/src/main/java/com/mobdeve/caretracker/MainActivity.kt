package com.mobdeve.caretracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobdeve.caretracker.databinding.LoginPageBinding

class MainActivity : AppCompatActivity() {
    private lateinit var loginPage: LoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        loginPage = LoginPageBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(loginPage.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.LinearLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton = loginPage.loginButton1

        loginButton.setOnClickListener {
            val intentToMainMenuViaSkip = Intent(this, HomeMenuPageActivity::class.java)
            intentToMainMenuViaSkip.putExtra("username", this.loginPage.loginIdentificationInput.text)
            startActivity(intentToMainMenuViaSkip)
            finish()
        }
    }
}