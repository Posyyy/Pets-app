package com.example.petsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
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
        emailEditText = findViewById(R.id.emailEditText)
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
        jsonBody.put("email", email)
        jsonBody.put("password", password)

        // Create the request
        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                Log.d("LoginActivity", "Response: $response")
                try {
                    // Check success and retrieve message using the correct key
                    val success = response.optInt("success", -1) // Default to -1 if not found
                    if (success == 1) {
                        Log.d("LoginActivity", "Login successful")
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        // Navigate to MainMenuActivity
                        val intent = Intent(this, MainMenuActivity::class.java)
                        startActivity(intent)
                        finish() // Finish the login activity
                    } else {
                        val message = response.optString("errormessage", "Unknown error")
                        Log.d("LoginActivity", "Error message: $message") // Log the error message
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Log.e("LoginActivity", "Error parsing response: ${e.localizedMessage}")
                    Toast.makeText(this, "Error parsing server response. Please try again.", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                // Log detailed error information
                Log.e("LoginActivity", "Network error: ${error.localizedMessage}")
                if (error.networkResponse != null) {
                    val statusCode = error.networkResponse.statusCode
                    Log.e("LoginActivity", "HTTP Status Code: $statusCode")
                    if (statusCode == 401) {
                        Toast.makeText(this, "Unauthorized access. Please check your email and password.", Toast.LENGTH_SHORT).show()
                    } else if (statusCode == 404) {
                        Toast.makeText(this, "Server not found. Please check your URL.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Network error. Please try again later.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Network error. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        )

        // Disable caching on the request
        request.setShouldCache(false)

        // Add the request to the queue
        Volley.newRequestQueue(this).add(request)
    }
}
