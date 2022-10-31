package com.miso.vinilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
    }
}