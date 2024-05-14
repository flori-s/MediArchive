package com.floris.mediarchive.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.floris.mediarchive.DBController

/**
 * Main activity of the application.
 * It initializes the database controller and starts the LoginActivity.
 */
class MainActivity : AppCompatActivity() {
    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the instance of the database controller
        DBController.getInstance()

        // Start the LoginActivity
        startActivity(Intent(this, LoginActivity::class.java))
    }
}