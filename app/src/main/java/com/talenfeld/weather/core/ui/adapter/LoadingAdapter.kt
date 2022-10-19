package com.talenfeld.weather.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.databinding.ItemLoadingBinding

object LoadingViewModel: ComparableItem {
    override fun id(): Any = content()

    override fun content(): Any = "LoadingViewModel"
}

class LoadingAdapter: SimpleDelegateAdapter<LoadingViewModel, ItemLoadingBinding>(LoadingViewModel::class.java) {

    override fun createViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): ItemLoadingBinding =
        ItemLoadingBinding.inflate(layoutInflater, parent, false)

    override fun bind(item: LoadingViewModel, binding: ItemLoadingBinding) {
        // do nothing
    }
}
