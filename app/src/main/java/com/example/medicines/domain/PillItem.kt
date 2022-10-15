package com.example.medicines.domain

data class PillItem(
    /* Medicine's title  */
    val title: String?,
    /* Shows how much medicine are left to take in days */
    val restOfPills: Double?,
    /* Id of medicine's icon */
    val imageId: Int?,
    /* Shows current condition of the medicine (using or not using)  */
    val condition: Boolean,
    /* The description of the method of administration and dosage */
    val descriptionOfTaking: String?,
    /* Medicine Id */
    var id: Int = UNDEFINED_ID
)
{
    companion object{
        const val UNDEFINED_ID = -1
    }
}

