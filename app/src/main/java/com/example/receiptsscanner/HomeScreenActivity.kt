package com.example.receiptsscanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val logout = findViewById<Button>(R.id.btnLogout)

        logout.setOnClickListener { v ->
            Intent(this, LoginActivity::class.java).also{
                startActivity(it)
            }
        }

    }
}