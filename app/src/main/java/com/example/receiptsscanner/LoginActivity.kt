package com.example.receiptsscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern




class LoginActivity : AppCompatActivity() {

    val EMAIL_REGEX = Pattern.compile(
        "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}",
        Pattern.CASE_INSENSITIVE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.btnLogin)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val shake = AnimationUtils.loadAnimation(this,R.anim.shake)

        login.setOnClickListener { v ->
            val emailStr = email.text.toString()
            val passwordStr = password.text.toString()
            when {
                emailStr.length == 0 -> {
                    Snackbar.make(
                        email,
                        "You must enter a valid email address",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    login.startAnimation(shake)
                }
                !EMAIL_REGEX.matcher(emailStr).find() -> {
                    Snackbar.make(
                        email,
                        "You must enter a valid email address",
                        Snackbar.LENGTH_LONG
                    ).show()
                    login.startAnimation(shake)
                }
                !passwordStr.contains("password") -> {
                    Snackbar.make(password, "Incorrect password", Snackbar.LENGTH_LONG).show()
                    login.startAnimation(shake)
                }else -> {
                Snackbar.make(email, "You are a genious", Snackbar.LENGTH_SHORT).show()
                Intent(this, HomeScreenActivity::class.java).also {
                    startActivity(it)
                }
            }
            }

        }

    }
}
