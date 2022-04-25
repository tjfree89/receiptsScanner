package com.example.receiptsscanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RecipeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)


        val back = findViewById<Button>(R.id.history_back_button)

        back.setOnClickListener { v ->
            Intent(this, RecipeActivity::class.java).also{
//                startActivity(it)
                finish()
            }
        }
    }



}