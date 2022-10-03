package com.example.medicines.domain

interface PillItemRepository {

    fun addPillItem(pillItem: PillItem)

    fun editPillItem(pillItem: PillItem)

    fun getPillItem(pillItemId: Int): PillItem

    fun getPillsList(): List<PillItem>

    fun removePillItem(pillItem: PillItem)
}