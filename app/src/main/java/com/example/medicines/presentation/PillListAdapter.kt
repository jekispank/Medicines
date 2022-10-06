package com.example.medicines.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicines.R
import com.example.medicines.databinding.ItemPillDisabledBinding
import com.example.medicines.databinding.ItemPillEnabledBinding
import com.example.medicines.domain.PillItem

class PillListAdapter : RecyclerView.Adapter<PillListAdapter.PillItemViewHolder>() {

    var count = 0
    var pillList = listOf<PillItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillItemViewHolder {
        Log.d("CreateViewHolder", "onCreateViewHolder, count ${++count}")

        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_pill_enabled
            VIEW_TYPE_DISABLED ->R.layout.item_pill_disabled
            else -> throw RuntimeException("Unknown viewType $viewType")
        }
        val item = LayoutInflater.from(parent.context).inflate(layout, parent,false)

        return PillItemViewHolder(item)
    }

    override fun onBindViewHolder(holder: PillItemViewHolder, position: Int) {
        val pillItem: PillItem = pillList[position]
        holder.apply {
            item.setOnLongClickListener { true }
            tvTitle.text = pillItem.title
            tvRestOfPills.text = pillItem.restOfPills.toString()
            descOfTaking.text = pillItem.descriptionOfTaking
        }
    }

    override fun getItemCount(): Int {
        return pillList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = pillList[position]
        return if (item.condition) VIEW_TYPE_ENABLED
        else VIEW_TYPE_DISABLED
    }

    class PillItemViewHolder(val item: View) :
        RecyclerView.ViewHolder(item) {

            val tvTitle = item.findViewById<TextView>(R.id.tv_title)
            val tvRestOfPills = item.findViewById<TextView>(R.id.rest_of_pills)
            val descOfTaking = item.findViewById<TextView>(R.id.desc_of_taking)

    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 5
    }
}