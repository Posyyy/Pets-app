package com.example.petsapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject

class VoteActivity : AppCompatActivity() {

    private lateinit var petImageView: ImageView
    private lateinit var petNameTextView: TextView
    private lateinit var upvoteButton: Button
    private lateinit var downvoteButton: Button
    private var petId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)

        petImageView = findViewById(R.id.petImageView)
        petNameTextView = findViewById(R.id.petNameTextView)
        upvoteButton = findViewById(R.id.upvoteButton)
        downvoteButton = findViewById(R.id.downvoteButton)

        fetchPet()

        upvoteButton.setOnClickListener {
            petId?.let { voteOnPet(it, "upvote") }
        }

        downvoteButton.setOnClickListener {
            petId?.let { voteOnPet(it, "downvote") }
        }
    }

    private fun fetchPet() {
        val url = "https://www.jwuclasses.com/ugly/get_pet"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val success = response.getBoolean("success")
                if (success) {
                    val pet = response.getJSONObject("pet")
                    petId = pet.getInt("id")
                    val petName = pet.getString("name")
                    val petImageUrl = pet.getString("image_url")

                    petNameTextView.text = petName
                    Glide.with(this).load(petImageUrl).into(petImageView)
                } else {
                    Toast.makeText(this, "Failed to load pet.", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    private fun voteOnPet(petId: Int, voteType: String) {
        val url = "https://www.jwuclasses.com/ugly/vote"
        val params = HashMap<String, Any>()
        params["pet_id"] = petId
        params["vote"] = voteType
        val jsonObject = JSONObject(params as Map<*, *>)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                val success = response.getBoolean("success")
                if (success) {
                    Toast.makeText(this, "Vote submitted!", Toast.LENGTH_SHORT).show()
                    fetchPet() // Fetch the next pet to vote on
                } else {
                    val errorMessage = response.getString("errormessage")
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }
}
