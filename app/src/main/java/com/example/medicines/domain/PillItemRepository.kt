package com.example.medicines.domain

import androidx.lifecycle.LiveData

interface PillItemRepository {

    fun addPillItem(pillItem: PillItem)

    fun editPillItem(pillItem: PillItem)

    fun getPillItem(pillItemId: Int): PillItem

    fun getPillsList(): LiveData<List<PillItem>>

    fun removePillItem(pillItem: PillItem)
}