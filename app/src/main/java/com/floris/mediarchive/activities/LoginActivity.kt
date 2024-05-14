package com.floris.mediarchive.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.floris.mediarchive.DBController
import com.floris.mediarchive.R
import com.floris.mediarchive.isValidPassword
import com.floris.mediarchive.toSHA256

/**
 * Activity for user login.
 * It handles user input validation and communicates with the database to authenticate the user.
 * @author Floris
 */
class LoginActivity : AppCompatActivity() {
    // Database controller instance
    val db = DBController.getInstance()

    // User information variables
    var personID = 0
    var patientID = 0
    var failedAttempts = 0

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    /**
     * Handles the login process when the login button is clicked.
     * @param view The view that was clicked.
     */
    fun login(view: View) {
        // Get the SharedPreferences
        val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Check if the account is blocked
        if (sharedPreferences.getBoolean("isBlocked", false)) {
            // Show an error message
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Too many failed attempts")
            builder.setMessage("Your account is blocked. Please contact the administrator.")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        } else {
            // Get the email and password fields
            val usernameEdittext =
                findViewById<EditText>(R.id.editTextTextUsername_LoginActivity_xml)
            val passwordEditText =
                findViewById<EditText>(R.id.editTextTextPassword_LoginActivity_xml)

            // Check if the email and password fields are not empty
            if (usernameEdittext.text.toString().isNotEmpty() && passwordEditText.text.toString()
                    .isNotEmpty()
            ) {
                // Check if the email and password fields are valid
                if (passwordEditText.text.toString().isValidPassword()) {
                    val username = usernameEdittext.text.toString()
                    val hashedPassword = passwordEditText.text.toString().toSHA256()

                    // Check if the user exists in the database
                    if (db.checkCredentials(username, hashedPassword)) {
                        // Reset the failed attempts counter
                        failedAttempts = 0
                        editor.putBoolean("isBlocked", false)
                        editor.apply()
                        db.unblockAccount(username)

                        // Get the patientID of the user
                        patientID = db.getPatientID(username) as Int

                        // Get the personID of the user
                        personID = db.getPersonID(username) as Int

                        // Put the patientID and personID in the SharedPreferences
                        editor.putInt("patientID", patientID)
                        editor.putInt("personID", personID)

                        // Apply the changes
                        editor.apply()
                        // Start the ViewActivity
                        startActivity(Intent(this, DataViewActivity::class.java))
                    } else {
                        // Increment the failed attempts counter
                        failedAttempts++
                        if (failedAttempts >= 3) {
                            db.blockAccount(username)
                            editor.putBoolean("isBlocked", true)
                            editor.apply()
                        }
                        usernameEdittext.error = "Invalid username or password!"
                        passwordEditText.error = "Invalid username or password!"
                    }
                } else {
                    passwordEditText.error = "Invalid password !"
                }
            } else {
                if (usernameEdittext.text.toString().isEmpty()) {
                    usernameEdittext.error = "username is required !"
                }
                if (passwordEditText.text.toString().isEmpty()) {
                    passwordEditText.error = "Password is required !"
                }
            }
        }
    }

    /**
     * Shows the password requirements when the info button is clicked.
     * @param view The view that was clicked.
     */
    fun showPasswordRequirements(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Wachtwoord vereisten")
        builder.setMessage(
            "Wachtwoord moet minimaal: \n" +
                    "- 8 karakters lang zijn. \n" +
                    "- 1 letter bevatten. \n" +
                    "- 1 cijfer bevatten. \n" +
                    "- 1 speciaal karakter bevatten. (@#\$%^&+=!)"
        )
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    /**
     * Starts the RegisterActivity when the register button is clicked.
     * @param view The view that was clicked.
     */
    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}