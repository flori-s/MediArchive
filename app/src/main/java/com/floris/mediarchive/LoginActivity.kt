package com.floris.mediarchive

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {
        // Get the email and password fields
        val emailEdittext = findViewById<EditText>(R.id.editTextTextEmailAddress_LoginActivity_xml)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword_LoginActivity_xml)

        // Check if the email and password fields are not empty
        if (emailEdittext.text.toString().isNotEmpty() && passwordEditText.text.toString()
                .isNotEmpty()
        ) {
            // Check if the email and password fields are valid
            if (emailEdittext.text.toString().isValidEmail() && passwordEditText.text.toString()
                    .isValidPassword()
            ) {
                val email = emailEdittext.text.toString()
                val hasedPassword = passwordEditText.text.toString().toSHA256()

                println("Email: $email")
                println("Password: $hasedPassword")

            }else
            {
                when {
                    !emailEdittext.text.toString().isValidEmail() -> emailEdittext.error = "Invalid email!"
                    !passwordEditText.text.toString().isValidPassword() -> passwordEditText.error = "Invalid password!"
                }
            }
        } else {
            when {
                emailEdittext.text.toString().isEmpty() -> emailEdittext.error = "Email is required!"
                passwordEditText.text.toString().isEmpty() -> passwordEditText.error = "Password is required!"
            }
        }
    }

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
}