package com.example.medicines.domain

class EditPillItemUseCase(private val pillItemRepository: PillItemRepository) {

    fun editPillItem(pillItem: PillItem){
        pillItemRepository.editPillItem(pillItem)

    }
}