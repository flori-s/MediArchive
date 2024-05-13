package com.floris.mediarchive.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.floris.mediarchive.DBController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Get the instance of the database controller
        DBController.getInstance()

        // Start the LoginActivity
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}