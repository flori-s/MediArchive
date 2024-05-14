package com.floris.mediarchive.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.floris.mediarchive.R
import com.floris.mediarchive.classes.MedicalRecord

/**
 * Adapter for the RecyclerView that displays the list of medical records.
 * @author Floris
 *
 * @property medicalRecords The list of medical records to display.
 */
class MedicalRecordAdapter(private val medicalRecords: List<MedicalRecord>) : RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder>() {

    /**
     * ViewHolder for the RecyclerView items.
     *
     * @property itemView The view of the RecyclerView item.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diagnosis: TextView = itemView.findViewById(R.id.diagnosis)
        val treatmentDate: TextView = itemView.findViewById(R.id.treatment_date)
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medical_record_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicalRecord = medicalRecords[position]
        holder.diagnosis.text = medicalRecord.diagnosis
        holder.treatmentDate.text = medicalRecord.treatmentDate
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount() = medicalRecords.size
}