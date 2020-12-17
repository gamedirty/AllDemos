package com.example.refadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface AdapterDelegate<VH : RecyclerView.ViewHolder> {
    fun getItemViewType(any: Any): Int

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    fun bindViewHolder(holder: VH, any: Any)
}