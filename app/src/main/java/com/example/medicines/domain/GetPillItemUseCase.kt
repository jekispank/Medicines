package com.example.medicines.domain

class GetPillItemUseCase(private val pillItemRepository: PillItemRepository) {
    fun getPillItem(pillItemId: Int){
        pillItemRepository.getPillItem(pillItemId)
    }
}