package com.example.receiptsscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.btnLogin)

        login.setOnClickListener { v ->
            Intent(this, HomeScreenActivity::class.java).also{
                startActivity(it)
            }
        }

    }
}