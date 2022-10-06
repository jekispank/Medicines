package com.example.medicines.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.medicines.domain.PillItem

class PillItemDiffCallback: DiffUtil.ItemCallback<PillItem>() {
    override fun areItemsTheSame(oldItem: PillItem, newItem: PillItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PillItem, newItem: PillItem): Boolean {
        return oldItem == newItem
    }
}