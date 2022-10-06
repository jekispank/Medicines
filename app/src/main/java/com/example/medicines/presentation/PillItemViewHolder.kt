package com.example.medicines.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicines.R

class PillItemViewHolder(val item: View) :
    RecyclerView.ViewHolder(item) {

    val tvTitle = item.findViewById<TextView>(R.id.tv_title)
    val tvRestOfPills = item.findViewById<TextView>(R.id.rest_of_pills)
    val descOfTaking = item.findViewById<TextView>(R.id.desc_of_taking)

}
