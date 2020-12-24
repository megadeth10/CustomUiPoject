package com.example.custom.widget.listview.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.myapplication.R

class FoldViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var headerLayout: LinearLayout  = itemView.findViewById(R.id.header_layout)
    var headerTitle: TextView  = itemView.findViewById(R.id.header_title)
    var foldCheckbox: CheckBox  = itemView.findViewById(R.id.fold_state)
    var childLayout: LinearLayout  = itemView.findViewById(R.id.child_layout)
}