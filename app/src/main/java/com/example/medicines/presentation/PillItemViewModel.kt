package com.example.medicines.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName


    val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _pillItem = MutableLiveData<PillItem>()
    val pillItem: LiveData<PillItem>
        get() = _pillItem

    private val _shouldCLoseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCLoseScreen

    fun getPillItem(pillItemId: Int) {
        val item = getPillItemUseCase.getPillItem(pillItemId)
//        _pillItem.value = item
    }

    fun addPillItem(inputPillTitle: String?, inputCount: String?, inputDesc: String?) {
        val name = parseName(inputPillTitle)
        val count = parseCount(inputCount)
        val description = parseDesc(inputDesc)
        val fieldsValid = checkValidateData(name, count, description)

        if (fieldsValid) {
            val pillItem = PillItem(name, count, 1, true, "")
            addPillItemUseCase.addPillItem(pillItem)
            finishWork()
        }

    }

    fun editPillItem(inputPillTitle: String?, inputCount: String?, inputDesc: String?) {
        val name = parseName(inputPillTitle)
        val count = parseCount(inputCount)
        val description = parseDesc(inputDesc)
        val fieldsValid = checkValidateData(name, count, description)

        if (fieldsValid) {
            _pillItem.value?.let {
                val item = it.copy(title = name, restOfPills = count)
                editPillItemUseCase.editPillItem(item)
                finishWork()
            }
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
        } catch (e: Exception) {
            0.0
        }
    }

    private fun checkValidateData(name: String, count: Double, description: String): Boolean {
        var result = true

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0.0) {
            TODO("Show invalidate count error")
            result = false
        }
        if (description.isBlank()) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    fun finishWork() {
        _shouldCLoseScreen.value = Unit
    }
}
