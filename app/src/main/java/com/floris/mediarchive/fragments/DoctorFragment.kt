package com.floris.mediarchive.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.floris.mediarchive.DBController
import com.floris.mediarchive.R
import com.floris.mediarchive.adapter.MedicalRecordAdapter
import com.floris.mediarchive.classes.MedicalRecord

/**
 * This fragment is responsible for displaying the doctor's information.
 * It fetches the data from the database and populates the UI elements.
 * @author Floris
 */
class DoctorFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_doctor, container, false)

        // Get shared preferences
        sharedPreferences = activity?.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)!!

        // Fill the UI elements with data
        fillEditText(view)
        return view
    }

    /**
     * This function fetches the doctor's information from the database and fills the UI elements.
     * @param view The view in which the UI elements are located.
     */
    fun fillEditText(view: View) {
        // Get the person ID from shared preferences
        val personID = sharedPreferences.getInt("personID", -1)

        // Initialize variables to hold the doctor's information
        var name = ""
        var birthDate = ""
        var gender = ""
        var address = ""
        var specialization = ""
        var years_of_experience = ""

        // Fetch the doctor's information from the database
        var doctor = db.getDoctorInfo(personID)
        if (doctor != null) {
            while (doctor.next()) {
                name = doctor.getString("name")
                birthDate = doctor.getString("birth_date")
                gender = doctor.getString("gender")
                address = doctor.getString("address")
                specialization = doctor.getString("specialization")
                years_of_experience = doctor.getString("years_of_experience")
            }
        }else{
            println("Doctor is null")
        }

        // Fill the UI elements with the fetched data
        view.findViewById<TextView>(R.id.TextViewDoctorName_DoctorFragment_xml).text = name
        view.findViewById<TextView>(R.id.TextViewBirthDate_DoctorFragment_xml).text = birthDate
        view.findViewById<TextView>(R.id.TextViewGender_DoctorFragment_xml).text = gender
        view.findViewById<TextView>(R.id.TextViewAddress_DoctorFragment_xml).text = address
        view.findViewById<TextView>(R.id.TextViewSpecialization_DoctorFragment_xml).text =
            specialization
        view.findViewById<TextView>(R.id.TextViewYearOfExperience_DoctorFragment_xml).text = "Years of Experience: $years_of_experience"
    }
}