package com.talenfeld.weather.core.ui.adapter.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.talenfeld.weather.core.ui.ComparableItem

abstract class SimpleDelegateAdapter<T: ComparableItem, V: ViewBinding>(
    private val clazz: Class<T>
): DelegateAdapter {

    abstract fun createViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): V

    abstract fun bind(item: T, binding: V)

    open fun onViewBindingCreated(binding: V) {
        // do nothing
    }

    override fun isForViewType(items: List<ComparableItem>, position: Int): Boolean =
        items[position].javaClass == clazz

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = createViewBinding(LayoutInflater.from(parent.context), parent)
        onViewBindingCreated(binding)
        return ViewHolder(binding)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        items: List<ComparableItem>,
        position: Int
    ) {
        val vh = holder as ViewHolder<V>
        bind(items[position] as T, vh.binding)
    }

    class ViewHolder<V: ViewBinding>(
        val binding: V
    ): RecyclerView.ViewHolder(binding.root)
}
