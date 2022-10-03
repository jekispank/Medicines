package com.example.medicines.data

import com.example.medicines.domain.PillItem
import com.example.medicines.domain.PillItem.Companion.UNDEFINED_ID
import com.example.medicines.domain.PillItemRepository

object PillListRepositoryImpl : PillItemRepository {

    private val pillList = mutableListOf<PillItem>()

    private var autoIncrementId = 0

    override fun addPillItem(pillItem: PillItem) {
        if (pillItem.id == UNDEFINED_ID) {
            pillItem.id = autoIncrementId++
        }
        pillList.add(pillItem)
    }

    override fun editPillItem(pillItem: PillItem) {
        val oldElement = getPillItem(pillItem.id)
        pillList.remove(oldElement)
        addPillItem(pillItem)
    }

    override fun getPillItem(pillItemId: Int): PillItem {
        return pillList.find { it.id == pillItemId } ?: throw RuntimeException(
            "Element with id $pillItemId hasn't found"
        )
    }

    override fun getPillsList(): List<PillItem> {
        return pillList.toMutableList()
    }

    override fun removePillItem(pillItem: PillItem) {
        pillList.remove(pillItem)
    }
}