package com.example.petsapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check login state
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            // Redirect to LoginActivity if not logged in
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_main_menu)

        // Initialize buttons
        val addPetButton: Button = findViewById(R.id.addPetButton)
        val voteButton: Button = findViewById(R.id.voteButton)
        val leaderboardButton: Button = findViewById(R.id.leaderboardButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)

        // Set button actions
        addPetButton.setOnClickListener {
            Toast.makeText(this, "Add Pet Clicked", Toast.LENGTH_SHORT).show()
            // Add navigation logic here
        }

        voteButton.setOnClickListener {
            Toast.makeText(this, "Vote Clicked", Toast.LENGTH_SHORT).show()
            // Add navigation logic here
        }

        leaderboardButton.setOnClickListener {
            Toast.makeText(this, "Leaderboard Clicked", Toast.LENGTH_SHORT).show()
            // Add navigation logic here
        }

        logoutButton.setOnClickListener {
            // Clear login state and redirect to LoginActivity
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
