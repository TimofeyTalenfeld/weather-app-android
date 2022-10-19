package com.talenfeld.weather.core.ui.adapter.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talenfeld.weather.core.ui.ComparableItem

interface DelegateAdapter {

    fun isForViewType(items: List<ComparableItem>, position: Int): Boolean

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<ComparableItem>,
        position: Int,
    )
}
