package com.example.refadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AnnotationAdapter(list: List<Any>? = null) :
    RecyclerView.Adapter<TSLViewHolder<*>>() {

    private val dataList by lazy { ArrayList<Any>() }

    init {
        list?.let { dataList.addAll(it) }
    }

    private val delegate by lazy { AnnotationAdapterDelegate }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TSLViewHolder<*> {
        return delegate.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return delegate.getItemViewType(dataList[position])
    }

    override fun onBindViewHolder(holder: TSLViewHolder<*>, position: Int) {
        delegate.bindViewHolder(holder, dataList[position])
    }

    override fun getItemCount() = dataList.size


}
