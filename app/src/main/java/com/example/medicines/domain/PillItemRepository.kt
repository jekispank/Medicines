package com.example.medicines.domain

interface PillItemRepository {

    fun addPillItem(pillItem: PillItem)

    fun editPillItem()

    fun getPillItem(pillItemId: Int)

    fun getPillsList(): List<PillItem>

    fun removePillItem(pillItem: PillItem)
}