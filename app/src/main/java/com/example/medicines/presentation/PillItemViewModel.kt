package com.example.medicines.presentation

import androidx.lifecycle.ViewModel
import com.example.medicines.data.PillListRepositoryImpl
import com.example.medicines.domain.AddPillItemUseCase
import com.example.medicines.domain.EditPillItemUseCase
import com.example.medicines.domain.GetPillItemUseCase
import com.example.medicines.domain.PillItem
import java.util.*

class PillItemViewModel : ViewModel() {

    private val repository = PillListRepositoryImpl

    private val getPillItemUseCase = GetPillItemUseCase(repository)
    private val addPillItemUseCase = AddPillItemUseCase(repository)
    private val editPillItemUseCase = EditPillItemUseCase(repository)

    fun getPillItem(pillItemId: Int) {
        getPillItemUseCase.getPillItem(pillItemId)

    }

    fun addPillItem(inputPillTitle: String?, inputCount: String?, inputDesc: String?) {
        val name = parseName(inputPillTitle)
        val count = parseCount(inputCount)
        val description = parseDesc(inputDesc)
        val fieldsValid = checkValidateData(name, count, description)

        if (fieldsValid) {
            val pillItem = PillItem(name, count, 1, true, "")
            addPillItemUseCase.addPillItem(pillItem)
        }

    }

    fun editPillItem(inputPillTitle: String?, inputCount: String?, inputDesc: String?) {
        val name = parseName(inputPillTitle)
        val count = parseCount(inputCount)
        val description = parseDesc(inputDesc)
        val fieldsValid = checkValidateData(name, count, description)

        if (fieldsValid) {
            val pillItem = PillItem(name, count, 1, true, "")
            editPillItemUseCase.editPillItem(pillItem)
        }

    }

    private fun parseName(inputPillTitle: String?): String {
        return inputPillTitle?.lowercase()
            ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            ?.trim() ?: ""
    }
    private fun parseDesc(inputDesc: String?): String {
        return inputDesc?.lowercase()
            ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            ?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Double {
        return try {
            inputCount?.trim()?.toDouble() ?: 0.0
        }catch(e: Exception) { 0.0 }
    }

    private fun checkValidateData(name: String, count: Double, description: String): Boolean{
        var result = true

        if (name.isBlank()) {
            TODO("Show invalidate name error")
            result = false
        }
        if (count <= 0.0) {
            TODO("Show invalidate count error")
            result = false
        }
        if (description.isBlank()){
            result = false
        }
        return result
    }
}
