package com.example.medicines.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.medicines.domain.PillItem

class PillListDiffCallback(
    private val oldPillList: List<PillItem>,
    private val newPillList: List<PillItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldPillList.size
    }

    override fun getNewListSize(): Int {
        return newPillList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldPillList[oldItemPosition]
        val newItem = newPillList[newItemPosition]
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldPillList[oldItemPosition]
        val newItem = newPillList[newItemPosition]
        return oldItem == newItem
    }

}