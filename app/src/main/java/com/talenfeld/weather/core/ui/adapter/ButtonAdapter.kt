package com.talenfeld.weather.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.databinding.ItemTitleBinding

data class TitleViewModel(
    val title: String
): ComparableItem {
    override fun id(): Any = title

    override fun content(): Any = this
}

class TitleAdapter: SimpleDelegateAdapter<TitleViewModel, ItemTitleBinding>(TitleViewModel::class.java) {
    override fun createViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemTitleBinding = ItemTitleBinding.inflate(layoutInflater, parent, false)

    override fun bind(item: TitleViewModel, binding: ItemTitleBinding) {
        binding.title.text = item.title
    }
}
