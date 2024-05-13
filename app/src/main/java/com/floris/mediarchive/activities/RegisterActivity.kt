package com.floris.mediarchive.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.floris.mediarchive.DBController
import com.floris.mediarchive.R
import com.floris.mediarchive.isValidEmail
import com.floris.mediarchive.isValidPassword
import com.floris.mediarchive.toSHA256
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    val db = DBController.getInstance()
    var name = ""
    var birthDate = ""
    var gender = ""
    var address = ""
    var username = ""
    var email = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupGenderSpinner()
    }

    fun register(view: View) {
        // Get the fields
        val nameEditText: EditText = findViewById(R.id.editTextName_RegisterActivity_xml)
        val birthDateEditText: EditText = findViewById(R.id.editTextDate_RegisterActivity_xml)
        val streetEditText: EditText = findViewById(R.id.editTextStreet_RegisterActivity_xml)
        val cityEditText: EditText = findViewById(R.id.editTextCity_RegisterActivity_xml)
        val usernameEditText: EditText = findViewById(R.id.editTextUsername_RegisterActivity_xml)
        val emailEditText: EditText = findViewById(R.id.editTextEmail_RegisterActivity_xml)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword_RegisterActivity_xml)

        // Get the values
        name = nameEditText.text.toString()
        birthDate = birthDateEditText.text.toString()
        address = streetEditText.text.toString() + ", " + cityEditText.text.toString()
        username = usernameEditText.text.toString()
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()

        // Check if the fields are not empty
        if (name.isEmpty() || birthDate.isEmpty() || address.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // Show error message
            when {
                name.isEmpty() -> nameEditText.error = "Name is required"
                birthDate.isEmpty() -> birthDateEditText.error = "Birth date is required"
                address.isEmpty() -> {
                    streetEditText.error = "Street is required"
                    cityEditText.error = "City is required"
                }
                username.isEmpty() -> usernameEditText.error = "Username is required"
                email.isEmpty() -> emailEditText.error = "Email is required"
                password.isEmpty() -> passwordEditText.error = "Password is required"
            }
        } else {
            // Check if the email and password are valid
            if (email.isValidEmail() && password.isValidPassword()) {
                // Hash the password
                val hashedPassword = password.toSHA256()

                // Check if the username is already taken
                if (db.checkUsername(username)) {
                    usernameEditText.error = "Username is already taken"
                    return
                }

                // Check if the email is already taken
                if (db.checkEmail(email)) {
                    emailEditText.error = "Email is already taken"
                    return
                }

                // Check valid birthdate
                val birthDateArray = birthDate.split("/")
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
                val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                if (birthDateArray[0].toInt() > currentYear || birthDateArray[1].toInt() > currentMonth || birthDateArray[2].toInt() > currentDay) {
                    birthDateEditText.error = "Invalid birth date"
                    return
                }

                // remove error messages
                nameEditText.error = null
                birthDateEditText.error = null
                streetEditText.error = null
                cityEditText.error = null
                usernameEditText.error = null
                emailEditText.error = null
                passwordEditText.error = null

                // Register the user
                db.registerUser(name, birthDate, gender, address, username, email, hashedPassword)

                // Clear the fields
                nameEditText.setText("")
                birthDateEditText.setText("")
                streetEditText.setText("")
                cityEditText.setText("")
                usernameEditText.setText("")
                emailEditText.setText("")
                passwordEditText.setText("")

                // Success message
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_LONG).show()

            } else {
                // Show error message
                when {
                    !email.isValidEmail() -> emailEditText.error = "Invalid email"
                    !password.isValidPassword() -> passwordEditText.error = "Invalid password"
                }
            }
        }
    }

    fun setupGenderSpinner() {
        val spinner: Spinner = findViewById(R.id.spinner_RegisterActivity_xml)
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_items,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                gender = spinner.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: handle the case where no item is selected
            }
        }
    }

    fun setupDatePicker(view: View) {
        val editText = view as EditText
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
        editText.isClickable = true

        editText.setOnClickListener {
            showDatePicker(editText)
        }
    }

    fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                editText.setText("$selectedYear/${selectedMonth + 1}/$selectedDay")
            }, year, month, day
        )

        datePickerDialog.show()
    }
}

