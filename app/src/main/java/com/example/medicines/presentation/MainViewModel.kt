package com.example.medicines.presentation

import androidx.lifecycle.ViewModel
import com.example.medicines.data.PillListRepositoryImpl
import com.example.medicines.domain.EditPillItemUseCase
import com.example.medicines.domain.GetPillListUseCase
import com.example.medicines.domain.PillItem
import com.example.medicines.domain.RemovePillItemUseCase

class MainViewModel : ViewModel() {

    private val repository = PillListRepositoryImpl

    private val getPillListUseCase = GetPillListUseCase(repository)
    private val removePillItemUseCase = RemovePillItemUseCase(repository)
    private val editPillItemUseCase = EditPillItemUseCase(repository)

    val piLLList = getPillListUseCase.getPillsList()


    fun removePillItem(pillItem: PillItem) {
        val removeElement = removePillItemUseCase.removePillItem(pillItem)
    }

    fun setCondition(pillItem: PillItem) {
        val changeCondition = pillItem.copy(condition = !pillItem.condition)
        editPillItemUseCase.editPillItem(changeCondition)
    }
}