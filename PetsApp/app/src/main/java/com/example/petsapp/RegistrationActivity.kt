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

class RegistrationActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize Views
        emailEditText = findViewById(R.id.emailEditText) // Updated ID
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.loginButton)

        // Set up register button click listener
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(name, email, password)
            }
        }

        // Set up login button click listener to navigate back to LoginActivity
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        val url = "https://www.jwuclasses.com/ugly/register" // Replace with your registration API endpoint

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
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    // Navigate to LoginActivity or another screen
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Finish the registration activity
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
