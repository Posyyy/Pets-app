package com.example.petsapp

import MultipartRequest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import java.io.File

class AddPetActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue
    private lateinit var petImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this)

        // UI components
        val petNameField: EditText = findViewById(R.id.petNameField)
        val petDescriptionField: EditText = findViewById(R.id.petDescriptionField)
        val uploadImageButton: Button = findViewById(R.id.uploadImageButton)
        val submitPetButton: Button = findViewById(R.id.submitPetButton)
        petImageView = findViewById(R.id.petImageView)

        uploadImageButton.setOnClickListener {
            // Open the gallery to pick an image
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        submitPetButton.setOnClickListener {
            val petName = petNameField.text.toString().trim()
            val petDescription = petDescriptionField.text.toString().trim()

            if (petName.isEmpty() || petDescription.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Replace this with the actual file path for the image
                val imagePath = "/path/to/your/image.jpg" // Example placeholder
                val file = File(imagePath)

                if (file.exists()) {
                    val params = mapOf(
                        "pet_name" to petName,
                        "pet_description" to petDescription
                    )

                    val request = MultipartRequest(
                        Request.Method.POST,
                        "https://www.jwuclasses.com/ugly/addPet", // Your server URL
                        { response ->
                            Toast.makeText(this, "Pet added successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        },
                        { error ->
                            error.printStackTrace()
                            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                        },
                        params,
                        "pet_image" to file
                    )

                    requestQueue.add(request)
                } else {
                    Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
}
