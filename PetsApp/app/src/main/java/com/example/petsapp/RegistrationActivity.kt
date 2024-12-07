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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, username, password)
            }
        }
    }

    private fun registerUser(email: String, username: String, password: String) {
        val url = "https://www.jwuclasses.com/ugly/register"
        val params = JSONObject()
        params.put("email", email)
        params.put("username", username)
        params.put("password", password)

        try {
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, params,
                { response ->
                    val success = response.optInt("success", 0) == 1
                    if (success) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        val errorMessage = response.optString("errormessage", "Registration failed")
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                },
                { error ->
                    Toast.makeText(this, "Network error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(this).add(jsonObjectRequest)
        } catch (e: Exception) {
            Toast.makeText(this, "Unexpected error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
