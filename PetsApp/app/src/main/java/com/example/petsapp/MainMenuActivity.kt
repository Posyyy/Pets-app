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
        setContentView(R.layout.activity_main_menu)

        // Check login state
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)


        // Initialize buttons
        val addPetButton: Button = findViewById(R.id.addPetButton)
        val voteButton: Button = findViewById(R.id.voteButton)
        val leaderboardButton: Button = findViewById(R.id.leaderboardButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)

        // Set button actions
        addPetButton.setOnClickListener {
            // Navigate to AddPetActivity
            val intent = Intent(this, AddPetActivity::class.java)
            startActivity(intent)
        }

        voteButton.setOnClickListener {
            // Navigate to VoteActivity
            val intent = Intent(this, VoteActivity::class.java)
            startActivity(intent)
        }

        leaderboardButton.setOnClickListener {
            // Navigate to LeaderboardActivity
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            // Clear login state and redirect to LoginActivity
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            finish()
        }
    }
}
