package com.floris.mediarchive.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.floris.mediarchive.DBController
import com.floris.mediarchive.R

/**
 * This fragment is responsible for displaying the nurse's information.
 * It fetches the data from the database and populates the UI elements.
 * @author Floris
 */
class NurseFragment : Fragment() {
    // Database controller instance
    val db = DBController.getInstance()
    // Shared preferences instance
    lateinit var sharedPreferences: SharedPreferences

    /**
     * This function is called to create and return the view hierarchy associated with the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nurse, container, false)

        // Get shared preferences
        sharedPreferences = activity?.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)!!

        // Fill the UI elements with data
        fillEditText(view)
        return view
    }

    /**
     * This function fetches the nurse's information from the database and fills the UI elements.
     * @param view The view in which the UI elements are located.
     */
    fun fillEditText(view: View) {
        // Get the person ID from shared preferences
        val personID = sharedPreferences.getInt("personID", -1)

        // Initialize variables to hold the nurse's information
        var name = ""
        var birthDate = ""
        var gender = ""
        var address = ""
        var department = ""
        var bignumber = ""

        // Fetch the nurse's information from the database
        var nurse = db.getNurseInfo(personID)
        if (nurse != null) {
            while (nurse.next()) {
                name = nurse.getString("name")
                birthDate = nurse.getString("birth_date")
                gender = nurse.getString("gender")
                address = nurse.getString("address")
                department = nurse.getString("department")
                bignumber = nurse.getString("big_number")
            }
        }

        // Fill the UI elements with the fetched data
        view.findViewById<TextView>(R.id.TextViewNurseName_NurseFragment_xml).text = name
        view.findViewById<TextView>(R.id.TextViewBirthDate_NurseFragment_xml).text = birthDate
        view.findViewById<TextView>(R.id.TextViewGender_NurseFragment_xml).text = gender
        view.findViewById<TextView>(R.id.TextViewAddress_NurseFragment_xml).text = address
        view.findViewById<TextView>(R.id.TextViewDepartment_NurseFragment_xml).text = department
        view.findViewById<TextView>(R.id.TextViewBigNumber_NurseFragment_xml).text = "Big Number: $bignumber"
    }
}