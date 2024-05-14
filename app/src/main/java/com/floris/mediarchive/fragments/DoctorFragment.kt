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

class DoctorFragment : Fragment() {
    val db = DBController.getInstance()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_doctor, container, false)

        sharedPreferences = activity?.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)!!

        fillEditText(view)
        return view
    }

    fun fillEditText(view: View) {
        val personID = sharedPreferences.getInt("personID", -1)

        var name = ""
        var birthDate = ""
        var gender = ""
        var address = ""
        var specialization = ""
        var years_of_experience = ""

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

        view.findViewById<TextView>(R.id.TextViewDoctorName_DoctorFragment_xml).text = name
        view.findViewById<TextView>(R.id.TextViewBirthDate_DoctorFragment_xml).text = birthDate
        view.findViewById<TextView>(R.id.TextViewGender_DoctorFragment_xml).text = gender
        view.findViewById<TextView>(R.id.TextViewAddress_DoctorFragment_xml).text = address
        view.findViewById<TextView>(R.id.TextViewSpecialization_DoctorFragment_xml).text =
            specialization
        view.findViewById<TextView>(R.id.TextViewYearOfExperience_DoctorFragment_xml).text = "Years of Experience: $years_of_experience"
    }
}