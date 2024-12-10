package com.example.petsapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var petNameInput: EditText
    private lateinit var petImageInput: EditText
    private lateinit var petScoreInput: EditText
    private lateinit var addEntryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        petNameInput = findViewById(R.id.petNameInput)
        petImageInput = findViewById(R.id.petImageInput)
        petScoreInput = findViewById(R.id.petScoreInput)
        addEntryButton = findViewById(R.id.addEntryButton)

        addEntryButton.setOnClickListener {
            val petName = petNameInput.text.toString()
            val petImage = petImageInput.text.toString()
            val petScore = petScoreInput.text.toString().toIntOrNull()

            if (petName.isBlank() || petImage.isBlank() || petScore == null) {
                Toast.makeText(this, "Please fill out all fields correctly", Toast.LENGTH_SHORT).show()
            } else {
                addLeaderboardEntry(petName, petImage, petScore)
            }
        }
    }

    private fun addLeaderboardEntry(petName: String, petImage: String, score: Int) {
        val url = "https://www.jwuclasses.com/ugly/addLeaderboardEntry"

        val requestBody = JSONObject().apply {
            put("pet_name", petName)
            put("pet_image", petImage)
            put("score", score)
        }

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            requestBody,
            { response ->
                try {
                    val success = response.getBoolean("success")
                    if (success) {
                        Toast.makeText(this, "Entry added successfully!", Toast.LENGTH_SHORT).show()
                        clearInputFields()
                    } else {
                        val message = response.getString("message")
                        Toast.makeText(this, "Failed to add entry: $message", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Unexpected response format", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(jsonRequest)
    }

    private fun clearInputFields() {
        petNameInput.text.clear()
        petImageInput.text.clear()
        petScoreInput.text.clear()
    }
}
