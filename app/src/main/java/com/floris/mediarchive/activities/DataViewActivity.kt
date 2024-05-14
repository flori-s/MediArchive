package com.floris.mediarchive.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.floris.mediarchive.DBController
import com.floris.mediarchive.R
import com.floris.mediarchive.fragments.DoctorFragment
import com.floris.mediarchive.fragments.NurseFragment
import com.floris.mediarchive.fragments.PatientFragment

/**
 * DataViewActivity is an activity that displays different fragments based on the role of the person logged in.
 * It checks if the person is a nurse, doctor, or patient and displays the corresponding fragment.
 * @author Floris
 */
class DataViewActivity : AppCompatActivity() {
    /**
     * This function is called when the activity is starting.
     * It sets the activity content from a layout resource and initializes the fragments.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_view)

        // Get the shared preferences and retrieve the person ID
        val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        val personID = sharedPreferences.getInt("personID", -1)

        // Get the instance of the database controller
        val db = DBController.getInstance()
        // Get the fragment manager
        val fragmentManager = supportFragmentManager
        // Start a series of edit operations on the Fragments associated with this FragmentManager.
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Check if the person is a nurse and display the NurseFragment if true
        if (db.checkIfNurse(personID)) {
            val fragment = NurseFragment()
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        }
        // Check if the person is a doctor and display the DoctorFragment if true
        else if (db.checkIfDoctor(personID)) {
            val fragment = DoctorFragment()
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        }
        // If the person is not a nurse or a doctor, display the PatientFragment
        else {
            val fragment = PatientFragment()
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        }

        // Commit the transaction
        fragmentTransaction.commit()
    }
}