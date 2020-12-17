package com.sovnem.recyclerviewdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_viewholder.*

class MyAdapter(private val data: List<String>) : RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_viewholder, null)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}


class MyViewHolder(private val inflate: View) : RecyclerView.ViewHolder(inflate),
    LayoutContainer {

    fun bind(str: String) {
    }

    override val containerView: View
        get() = inflate
}