package com.example.receiptsscanner

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    /*
    * When we have a screen that shows accummulated receipt data we can have a spinner
    * that uses a dropdown like video 2 of week 10, to select receipts by category, we can store
    *  all app screen options in a 3 dot options bar at the top of the screen
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val takePicture = findViewById<Button>(R.id.btnScanPage)
        val history = findViewById<Button>(R.id.btnHistoryPage)
        val logout = findViewById<Button>(R.id.btnLogout)


        takePicture.setOnClickListener { v ->
            Intent(this, TakePictureActivity::class.java).also{
                startActivity(it)
//                finish()
            }
        }
        history.setOnClickListener { v ->
            Intent(this, PurchaseHistoryActivity::class.java).also{
                startActivity(it)
//                finish()
            }
        }

        logout.setOnClickListener { v ->
            Intent(this, LoginActivity::class.java).also{
//                startActivity(it)
                finish()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true //insetad of super.onCreateOptionsMenu(menu) means we inflated our menu.
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
//            R.id.purchaseHistory ->}
//            R.id.purchaseHistoryText ->

        }
        return true //instead of super.onOptionsItemSelected(item)
    }
}