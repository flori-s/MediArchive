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

class PatientFragment : Fragment() {
    val db = DBController.getInstance()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patient, container, false)

        sharedPreferences = activity?.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)!!
        fillEditText(view)

        return view
    }

    fun fillEditText(view: View) {
        val patientID = sharedPreferences.getInt("patientID", -1)

        val medicalRecords = mutableListOf<MedicalRecord>()
        var name = ""
        var birthDate = ""
        var address = ""
        var email = ""
        var dokterName = ""
        var diagnosis = ""
        var treatmentDate = ""

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

        view.findViewById<TextView>(R.id.TextviewpatientName_ViewsActivty_xml).text = name
        view.findViewById<TextView>(R.id.TextViewpatientBirthDate_ViewsActivty_xml).text = birthDate
        view.findViewById<TextView>(R.id.TextViewpatientAddress_ViewsActivty_xml).text = address
        view.findViewById<TextView>(R.id.TextViewpatientEmail_ViewsActivty_xml).text = email
        view.findViewById<TextView>(R.id.TextViewdoctorName_ViewsActivty_xml).text = dokterName

        val recyclerView = view.findViewById<RecyclerView>(R.id.medicalRecordsList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MedicalRecordAdapter(medicalRecords)
    }
}
