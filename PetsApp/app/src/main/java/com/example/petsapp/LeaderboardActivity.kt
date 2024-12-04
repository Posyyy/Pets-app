package com.example.petsapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class LeaderboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        // Initialize UI components
        val leaderboardListView: ListView = findViewById(R.id.leaderboardListView)

        // Mock leaderboard data
        val leaderboardData = listOf(
            "1. Buddy - 100 votes",
            "2. Luna - 80 votes",
            "3. Charlie - 70 votes"
        )

        // Set up adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, leaderboardData)
        leaderboardListView.adapter = adapter
    }
}
