package com.example.petsapp



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.petsapp.LoginActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Link to activity_splash.xml
        setContentView(R.layout.activity_splash)

        // Delay for 3 seconds before navigating to LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close SplashActivity so it doesn’t stay in the back stack
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}
