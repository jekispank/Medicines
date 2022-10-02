package com.example.medicines.domain

class GetPillListUseCase(private val pillItemRepository: PillItemRepository) {

    fun getPillsList(): List<PillItem>{
        return pillItemRepository.getPillsList()
    }
}