package com.example.medicines.domain

data class PillItem(
    /* Medicine Id */
    val id: Int?,
    /* Medicine's title  */
    val title: String?,
    /* Shows how much medicine are left to take  */
    val restOfPills: Double?,
    /* Id of medicine's icon */
    val imageId: Int?,
    /* Shows current condition of the medicine (using or not using)  */
    val condition: Boolean,
    /* The description of the method of administration and dosage */
    val descriptionOfTaking: String?
)
