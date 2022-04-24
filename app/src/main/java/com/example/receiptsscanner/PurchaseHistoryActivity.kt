package com.example.receiptsscanner

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.text.Text

class PurchaseHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_history)

        val back = findViewById<Button>(R.id.history_back_button)
        val holder : String

        val arrayAdapter: ArrayAdapter<*>
        var receipts = arrayOf("first")

        var historyList = findViewById<ListView>(R.id.historyList)

        val intentValue = intent.getStringExtra("result")
        findViewById<TextView>(R.id.test).apply{
            holder = intentValue.toString()
        }

        receipts += holder

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, receipts)
        historyList.adapter = arrayAdapter

        back.setOnClickListener {
            Intent(this, MainActivity::class.java).also{
                startActivity(it)
            }
        }






    }
}