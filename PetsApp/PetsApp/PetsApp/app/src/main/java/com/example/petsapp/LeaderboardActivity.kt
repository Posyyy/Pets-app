package com.example.petsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LeaderboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample data
        val characters = listOf(
            Character("Fluffy", "https://example.com/fluffy.jpg"),
            Character("Whiskers", "https://example.com/whiskers.jpg"),
            Character("Spot", "https://example.com/spot.jpg")
        )

        val adapter = CharacterAdapter(characters)
        recyclerView.adapter = adapter
    }
}
