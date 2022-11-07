package com.miso.vinilos.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.miso.vinilos.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goToHomeCollector = findViewById<Button>(R.id.btn_login_collector)
        goToHomeCollector.setOnClickListener{
            goToHome()
        }

        val goToHomeGuest = findViewById<Button>(R.id.btn_login_guest)
        goToHomeGuest.setOnClickListener{
            goToHome()
        }
    }

    private fun goToHome() {
        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
    }
}