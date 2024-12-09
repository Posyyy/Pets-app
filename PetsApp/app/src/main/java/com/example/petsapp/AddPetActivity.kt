package com.example.petsapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class AddPetActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this)

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
                submitPetInfo(petName, petDescription)
            }
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
}
