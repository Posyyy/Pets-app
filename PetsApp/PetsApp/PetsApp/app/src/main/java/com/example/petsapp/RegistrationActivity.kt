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

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize Views
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.registerButton)

        // Set up register button click listener
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(username, password)
            }
        }
    }

    private fun registerUser(username: String, password: String) {
        val url = "https://your.api.endpoint/register" // Replace with your registration API endpoint

        // Create JSON payload
        val jsonBody = JSONObject()
        jsonBody.put("username", username)
        jsonBody.put("password", password)

        // Create the request
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                try {
                    // Parse response
                    val success = response.getBoolean("success")
                    if (success) {
                        Toast.makeText(this, "Registration successful. Please log in.", Toast.LENGTH_SHORT).show()
                        // Navigate back to the login screen
                        finish()
                    } else {
                        val message = response.getString("message")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
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
