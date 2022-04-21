package com.example.receiptsscanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeScreenActivity : AppCompatActivity() {
/*
* When we have a screen that shows accummulated receipt data we can have a spinner
* that uses a dropdown like video 2 of week 10, to select receipts by category, we can store
*  all app screen options in a 3 dot options bar at the top of the screen
* */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val logout = findViewById<Button>(R.id.btnLogout)
        val recogScreen = findViewById<Button>(R.id.btnRecog)

        logout.setOnClickListener { v ->
            Intent(this, LoginActivity::class.java).also{
                startActivity(it)
            }
        }

        recogScreen.setOnClickListener { v ->
            Intent(this, TextRecognitionActivity::class.java).also{
                startActivity(it)
            }
        }

    }
}