package com.floris.mediarchive.classes

/**
 * Data class representing a medical record.
 *
 * @property diagnosis The diagnosis of the medical condition.
 * @property treatmentDate The date of the treatment.
 * @author Floris
 */
data class MedicalRecord(
    val diagnosis: String,
    val treatmentDate: String
)