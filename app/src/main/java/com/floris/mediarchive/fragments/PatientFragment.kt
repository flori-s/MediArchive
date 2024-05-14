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
 * This fragment is responsible for displaying the patient's information.
 * It fetches the data from the database and populates the UI elements.
 * @author Floris
 */
class PatientFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_patient, container, false)

        // Get shared preferences
        sharedPreferences = activity?.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)!!

        // Fill the UI elements with data
        fillEditText(view)
        return view
    }

    /**
     * This function fetches the patient's information from the database and fills the UI elements.
     * @param view The view in which the UI elements are located.
     */
    fun fillEditText(view: View) {
        // Get the patient ID from shared preferences
        val patientID = sharedPreferences.getInt("patientID", -1)

        // Initialize variables to hold the patient's information
        val medicalRecords = mutableListOf<MedicalRecord>()
        var name = ""
        var birthDate = ""
        var address = ""
        var email = ""
        var dokterName = ""
        var diagnosis = ""
        var treatmentDate = ""

        // Fetch the patient's information from the database
        var patient = db.getPatientInfo(patientID)
        if (patient != null) {
            while (patient.next()) {
                name = patient.getString("name")
                birthDate = patient.getString("birth_date")
                address = patient.getString("address")
                email = patient.getString("email")
                dokterName = patient.getString("dokterName")
                diagnosis = patient.getString("diagnosis")
                treatmentDate = patient.getString("treatment_date")
                medicalRecords.add(MedicalRecord(diagnosis, treatmentDate))
            }
        }

        // Fill the UI elements with the fetched data
        view.findViewById<TextView>(R.id.TextviewpatientName_ViewsActivty_xml).text = name
        view.findViewById<TextView>(R.id.TextViewpatientBirthDate_ViewsActivty_xml).text = birthDate
        view.findViewById<TextView>(R.id.TextViewpatientAddress_ViewsActivty_xml).text = address
        view.findViewById<TextView>(R.id.TextViewpatientEmail_ViewsActivty_xml).text = email
        view.findViewById<TextView>(R.id.TextViewdoctorName_ViewsActivty_xml).text = dokterName

        // Set up the RecyclerView to display the medical records
        val recyclerView = view.findViewById<RecyclerView>(R.id.medicalRecordsList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MedicalRecordAdapter(medicalRecords)
    }
}