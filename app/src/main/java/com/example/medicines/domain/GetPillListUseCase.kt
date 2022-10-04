package com.example.medicines.domain

import androidx.lifecycle.LiveData

class GetPillListUseCase(private val pillItemRepository: PillItemRepository) {

    fun getPillsList(): LiveData<List<PillItem>>{
        return pillItemRepository.getPillsList()
    }
}