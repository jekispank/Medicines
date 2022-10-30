package com.example.medicines.domain

class AddPillItemUseCase(private val pillItemRepository: PillItemRepository) {

    fun addPillItem(pillItem: PillItem){
        pillItemRepository.addPillItem(pillItem)

    }
}