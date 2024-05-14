package com.floris.mediarchive.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.floris.mediarchive.DBController
import com.floris.mediarchive.R
import com.floris.mediarchive.adapter.MedicalRecordAdapter
import com.floris.mediarchive.classes.MedicalRecord

class ViewActivity : AppCompatActivity() {
    val db = DBController.getInstance()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        fillEditText()
    }

    // fill edit text with the data of the selected media
    fun fillEditText() {
        val patientID = sharedPreferences.getInt("patientID", -1)

        if (patientID == -1) {

        } else {
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
                    name = patient!!.getString("name")
                    birthDate = patient.getString("birth_date")
                    address = patient.getString("address")
                    email = patient.getString("email")
                    dokterName = patient.getString("dokterName")
                    diagnosis = patient.getString("diagnosis")
                    treatmentDate = patient.getString("treatment_date")
                    medicalRecords.add(MedicalRecord(diagnosis, treatmentDate))
                }
            }

            // fill the textview with the data
            findViewById<TextView>(R.id.TextviewpatientName_ViewsActivty_xml).text = name
            findViewById<TextView>(R.id.TextViewpatientBirthDate_ViewsActivty_xml).text = birthDate
            findViewById<TextView>(R.id.TextViewpatientAddress_ViewsActivty_xml).text = address
            findViewById<TextView>(R.id.TextViewpatientEmail_ViewsActivty_xml).text = email
            findViewById<TextView>(R.id.TextViewdoctorName_ViewsActivty_xml).text = dokterName

            val recyclerView = findViewById<RecyclerView>(R.id.medicalRecordsList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = MedicalRecordAdapter(medicalRecords)
        }
    }
}