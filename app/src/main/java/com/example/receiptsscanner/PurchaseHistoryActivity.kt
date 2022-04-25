package com.example.receiptsscanner

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PurchaseHistoryActivity : AppCompatActivity() {

    lateinit var test:TextView
    lateinit var test2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)

        test = findViewById(R.id.test)
        val back = findViewById<Button>(R.id.history_back_button)

        back.setOnClickListener { v ->
            Intent(this, PurchaseHistoryActivity::class.java).also{
                finish()
            }
        }

        val intentValue = intent.getStringExtra("item")

        test.text = intentValue.toString()

    }



}