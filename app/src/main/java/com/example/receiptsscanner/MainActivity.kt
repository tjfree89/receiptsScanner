package com.example.receiptsscanner

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val takePicture = findViewById<Button>(R.id.btnScanPage)
        val history = findViewById<Button>(R.id.btnHistoryPage)
        val logout = findViewById<Button>(R.id.btnLogout)


        takePicture.setOnClickListener { v ->
            Intent(this, TakePictureActivity::class.java).also{
                startActivity(it)
            }
        }
        history.setOnClickListener { v ->
            Intent(this, PurchaseHistoryActivity::class.java).also{
                startActivity(it)
            }
        }

        logout.setOnClickListener { v ->
            Intent(this, LoginActivity::class.java).also{
                finish()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when {
            item.itemId == R.id.Logout -> {
                finish()
            }
            item.itemId == R.id.purchaseHistory || item.itemId == R.id.purchaseHistoryText -> {

                Intent(this, PurchaseHistoryActivity::class.java).also{
                    startActivity(it)
                }
            }
            item.itemId == R.id.takePicture ->{
                Intent(this, TakePictureActivity::class.java).also{
                    startActivity(it)
                }
            }

        }
        return true
    }
}