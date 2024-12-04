package com.example.petsapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)

        // Initialize UI components with correct IDs
        val petImageView: ImageView = findViewById(R.id.petImageView)
        val petNameTextView: TextView = findViewById(R.id.petNameTextView)
        val voteButton: Button = findViewById(R.id.voteButton)

        // Mock data
        val petName = "Buddy"
        petNameTextView.text = petName

        // TODO: Load the pet's image into petImageView
        // Example using Glide:
        // Glide.with(this).load("image_url").into(petImageView)

        // Vote button logic
        voteButton.setOnClickListener {
            // TODO: Send the vote to the backend
            Toast.makeText(this, "Voted for $petName!", Toast.LENGTH_SHORT).show()
        }
    }
}
