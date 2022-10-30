package com.example.medicines.domain

class RemovePillItemUseCase(private val pillItemRepository: PillItemRepository) {

    fun removePillItem(pillItem: PillItem){
        pillItemRepository.removePillItem(pillItem)

    }
}