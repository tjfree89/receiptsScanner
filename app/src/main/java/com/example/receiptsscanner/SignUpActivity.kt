package com.example.receiptsscanner

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
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
                finish().also{
                    Intent(this, PurchaseHistoryActivity::class.java).also{
                        startActivity(it)
                    }
                }

            }
//            R.id.purchaseHistory ->}
//            R.id.purchaseHistoryText ->

        }
        return true //instead of super.onOptionsItemSelected(item)
    }


}