package com.example.petsapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.IOException

class AddPetActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue
    private lateinit var petImageView: ImageView // ImageView for displaying the chosen image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this)

        // Initialize UI components
        val petNameField: EditText = findViewById(R.id.petNameField)
        val petDescriptionField: EditText = findViewById(R.id.petDescriptionField)
        petImageView = findViewById(R.id.petImageView) // Initialize ImageView
        val uploadImageButton: Button = findViewById(R.id.uploadImageButton)
        val submitPetButton: Button = findViewById(R.id.submitPetButton)

        // Set up the upload button click listener
        uploadImageButton.setOnClickListener {
            Toast.makeText(this, "Upload Image button clicked", Toast.LENGTH_SHORT).show()
            // Add your image picking logic here (e.g., use an Intent to open the gallery)
            pickImageFromGallery()
        }

        // Set up the submit button click listener
        submitPetButton.setOnClickListener {
            val petName = petNameField.text.toString().trim()
            val petDescription = petDescriptionField.text.toString().trim()

            if (petName.isEmpty() || petDescription.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                submitPetInfo(petName, petDescription)
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri = data.data!!
            petImageView.setImageURI(imageUri)
            // You can add more logic here to handle image processing or storage
        }
    }

    private fun submitPetInfo(petName: String, petDescription: String) {
        val url = "https://www.jwuclasses.com/ugly/addPet" // Replace with your server endpoint

        // Create a POST request with Volley
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")
                    if (success) {
                        Toast.makeText(this, "Pet added successfully!", Toast.LENGTH_SHORT).show()
                        finish() // Finish the activity
                    } else {
                        val message = jsonResponse.getString("message")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing server response", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(this, "Network error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["pet_name"] = petName
                params["pet_description"] = petDescription
                return params
            }
        }

        // Add the request to the RequestQueue
        requestQueue.add(stringRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel any pending requests when the activity is destroyed
        requestQueue.cancelAll(this)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }
}
