package com.example.petsapp

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VoteActivity : AppCompatActivity() {

    private lateinit var petNameTextView: TextView
    private lateinit var ratingGroup: RadioGroup
    private lateinit var submitVoteButton: Button
    private lateinit var fetchNewPetButton: Button
    private lateinit var doneVotingButton: Button
    private lateinit var goBackButton: Button

    private var currentPetId: Int = -1
    private val sessionToken: String by lazy {
        getSharedPreferences("user_prefs", MODE_PRIVATE).getString("session_token", "") ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)

        petNameTextView = findViewById(R.id.petNameTextView)
        ratingGroup = findViewById(R.id.ratingGroup)
        submitVoteButton = findViewById(R.id.submitVoteButton)
        fetchNewPetButton = findViewById(R.id.fetchNewPetButton)
        doneVotingButton = findViewById(R.id.doneVotingButton)
        goBackButton = findViewById(R.id.goBackButton)

        // Fetch initial pet
        fetchNewPet()

        // Submit Vote Button
        submitVoteButton.setOnClickListener { submitVote() }

        // Fetch New Pet Button
        fetchNewPetButton.setOnClickListener { fetchNewPet() }

        // Done Voting Button
        doneVotingButton.setOnClickListener { finish() }

        // Go Back Button
        goBackButton.setOnClickListener { onBackPressed() }
    }

    private fun fetchNewPet() {
        val url = "https://www.jwuclasses.com/ugly/getPet" // Replace with your actual endpoint

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    currentPetId = response.getInt("pet_id")
                    val petName = response.getString("pet_name")
                    petNameTextView.text = petName
                } catch (e: Exception) {
                    Toast.makeText(this, "Failed to fetch pet details", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        jsonRequest.setShouldCache(false)
        Volley.newRequestQueue(this).add(jsonRequest)
    }

    private fun submitVote() {
        val selectedRatingId = ratingGroup.checkedRadioButtonId
        if (selectedRatingId == -1) {
            Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show()
            return
        }

        val rating = when (selectedRatingId) {
            R.id.rating1 -> 1
            R.id.rating2 -> 2
            R.id.rating3 -> 3
            R.id.rating4 -> 4
            R.id.rating5 -> 5
            else -> 0
        }

        val url = "https://example.com/api/voteOnPet" // Replace with your actual endpoint
        val jsonBody = JSONObject()
        jsonBody.put("token", sessionToken)
        jsonBody.put("pet_id", currentPetId)
        jsonBody.put("rating", rating)

        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            { response ->
                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(this, "Vote submitted successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to submit vote", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Unexpected response", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        jsonRequest.setShouldCache(false)
        Volley.newRequestQueue(this).add(jsonRequest)
    }
}
