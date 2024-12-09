package com.example.petsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Views
        emailEditText = findViewById(R.id.emailEditText) // Updated ID
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        // Set up login button click listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        // Set up register button click listener to navigate to RegistrationActivity
        registerButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        val url = "https://www.jwuclasses.com/ugly/login" // Replace with your login API endpoint

        // Create JSON payload
        val jsonBody = JSONObject()
        jsonBody.put("email", email) // Updated key to "email"
        jsonBody.put("password", password)

        // Create the request
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                val success = response.getString("success") // Expects a string "true" or "false"
                if (success == "true") {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    // Proceed to the main menu activity or the next screen
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                    finish() // Finish the login activity
                } else {
                    val message = response.getString("message")
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                error.printStackTrace()
                Toast.makeText(this, "Network error. Please try again.", Toast.LENGTH_SHORT).show()
            }
        )

        // Disable caching on the request
        request.setShouldCache(false)

        // Add the request to the queue
        Volley.newRequestQueue(this).add(request)
    }
}
