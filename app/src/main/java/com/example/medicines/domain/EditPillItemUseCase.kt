package com.example.medicines.domain

class EditPillItemUseCase(private val pillItemRepository: PillItemRepository) {

    fun editPillItem(){
        pillItemRepository.editPillItem()

    }
}