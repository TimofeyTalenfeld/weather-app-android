package com.talenfeld.weather.core.ui.adapter.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.util.indexOrNull

class DiffAdapter(
    private val delegateAdapters: List<DelegateAdapter>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    constructor(vararg delegateAdapters: DelegateAdapter): this(delegateAdapters.toList())

    private var items: MutableList<ComparableItem> = mutableListOf()

    override fun getItemCount(): Int = items.size

    fun swapData(newData: List<ComparableItem>) {
        val newItems = ArrayList(newData)
        val diffResult = calculateDiff(items, newItems)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getAdapterForItemViewType(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        return getAdapterForItemViewType(viewType).onBindViewHolder(holder, items, position)
    }

    override fun getItemViewType(position: Int): Int = checkNotNull(
        delegateAdapters.indexOrNull { adapter -> adapter.isForViewType(items, position) }
    ) { "Item view type not found for position $position" }

    private fun calculateDiff(oldData: List<ComparableItem>, newData: List<ComparableItem>): DiffUtil.DiffResult =
        DiffUtil.calculateDiff(DiffUtilCallback(oldData, newData))

    private fun getAdapterForItemViewType(itemViewType: Int): DelegateAdapter =
        delegateAdapters[itemViewType]

    private class DiffUtilCallback(
        private val oldList: List<ComparableItem>,
        private val newList: List<ComparableItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id() == newList[newItemPosition].id()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].content() == newList[newItemPosition].content()
    }
}
