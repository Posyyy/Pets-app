package com.example.petsapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddPetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)

        // Initialize UI components
        val petNameField: EditText = findViewById(R.id.petNameField)
        val petDescriptionField: EditText = findViewById(R.id.petDescriptionField)
        val uploadImageButton: Button = findViewById(R.id.uploadImageButton)
        val submitPetButton: Button = findViewById(R.id.submitPetButton)

        // Set up the upload button click listener (if you have an image uploading feature)
        uploadImageButton.setOnClickListener {
            Toast.makeText(this, "Upload Image button clicked", Toast.LENGTH_SHORT).show()
            // Add your logic for image upload here
        }

        // Set up the submit button click listener
        submitPetButton.setOnClickListener {
            val petName = petNameField.text.toString().trim()
            val petDescription = petDescriptionField.text.toString().trim()

            if (petName.isEmpty() || petDescription.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Perform your logic for saving the pet information here
                Toast.makeText(this, "Pet information submitted successfully!", Toast.LENGTH_SHORT).show()
                // Optionally navigate back or show confirmation
                finish() // Finish this activity and return to the previous one
            }
        }
    }
}
