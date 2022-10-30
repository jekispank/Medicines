package com.example.medicines.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medicines.domain.PillItem
import com.example.medicines.domain.PillItem.Companion.UNDEFINED_ID
import com.example.medicines.domain.PillItemRepository

object PillListRepositoryImpl : PillItemRepository {

    private val pillListLD = MutableLiveData<List<PillItem>>()
    private val pillList = sortedSetOf<PillItem>({o1, o2 -> o1.id.compareTo(o2.id)})

    private var autoIncrementId = 0

    init {
        for (i in 0..25){
            val item = PillItem("Name $i", (1.0 * i), (i*2), true, "$i a day", i)
            addPillItem(item)
        }
    }

    override fun addPillItem(pillItem: PillItem) {
        if (pillItem.id == UNDEFINED_ID) {
            pillItem.id = autoIncrementId++
        }
        pillList.add(pillItem)
        updatePillList()
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

    override fun getPillsList(): LiveData<List<PillItem>> {
        return pillListLD
    }

    override fun removePillItem(pillItem: PillItem) {
        pillList.remove(pillItem)
        updatePillList()
    }

    private fun updatePillList() {
        pillListLD.value = pillList.toList()
    }
}