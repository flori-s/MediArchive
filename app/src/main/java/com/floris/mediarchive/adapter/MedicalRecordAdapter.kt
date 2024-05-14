package com.floris.mediarchive.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.floris.mediarchive.R
import com.floris.mediarchive.classes.MedicalRecord

class MedicalRecordAdapter(private val medicalRecords: List<MedicalRecord>) : RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diagnosis: TextView = itemView.findViewById(R.id.diagnosis)
        val treatmentDate: TextView = itemView.findViewById(R.id.treatment_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medical_record_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicalRecord = medicalRecords[position]
        holder.diagnosis.text = medicalRecord.diagnosis
        holder.treatmentDate.text = medicalRecord.treatmentDate
    }

    override fun getItemCount() = medicalRecords.size
}