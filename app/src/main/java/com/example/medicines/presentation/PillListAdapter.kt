package com.example.medicines.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.medicines.R
import com.example.medicines.domain.PillItem

class PillListAdapter :
    ListAdapter<PillItem, PillItemViewHolder>(PillItemDiffCallback()) {

    var onPillItemLongClickListener: ((PillItem) -> Unit)? = null
    var onPillItemClickListener: ((PillItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillItemViewHolder {

        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_pill_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_pill_disabled
            else -> throw RuntimeException("Unknown viewType $viewType")
        }
        val item = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return PillItemViewHolder(item)
    }

    override fun onBindViewHolder(holder: PillItemViewHolder, position: Int) {

        val pillItem: PillItem = getItem(position)
        holder.apply {
            item.setOnLongClickListener {
                onPillItemLongClickListener?.invoke(pillItem)
                true
            }
            item.setOnClickListener() {
                onPillItemClickListener?.invoke(pillItem)
            }
            tvTitle.text = pillItem.title
            tvRestOfPills.text = pillItem.restOfPills.toString()
            descOfTaking.text = pillItem.descriptionOfTaking
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.condition) VIEW_TYPE_ENABLED
        else VIEW_TYPE_DISABLED
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 5
    }
}