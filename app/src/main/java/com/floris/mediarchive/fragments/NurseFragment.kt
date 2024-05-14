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

class NurseFragment : Fragment() {
    val db = DBController.getInstance()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nurse, container, false)

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
        var department = ""
        var bignumber = ""

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

        view.findViewById<TextView>(R.id.TextViewNurseName_NurseFragment_xml).text = name
        view.findViewById<TextView>(R.id.TextViewBirthDate_NurseFragment_xml).text = birthDate
        view.findViewById<TextView>(R.id.TextViewGender_NurseFragment_xml).text = gender
        view.findViewById<TextView>(R.id.TextViewAddress_NurseFragment_xml).text = address
        view.findViewById<TextView>(R.id.TextViewDepartment_NurseFragment_xml).text = department
        view.findViewById<TextView>(R.id.TextViewBigNumber_NurseFragment_xml).text = "Big Number: $bignumber"
    }
}
