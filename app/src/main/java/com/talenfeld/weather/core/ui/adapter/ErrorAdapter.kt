package com.talenfeld.weather.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.databinding.ItemErrorBinding

data class ErrorViewModel(
    val errorHintResId: Int,
    val retryButtonTextResId: Int
): ComparableItem {
    override fun id(): Any = "ErrorViewModel"

    override fun content(): Any = this
}

class ErrorAdapter(
    private val onRetryClicked: () -> Unit
): SimpleDelegateAdapter<ErrorViewModel, ItemErrorBinding>(ErrorViewModel::class.java) {

    override fun createViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): ItemErrorBinding =
        ItemErrorBinding.inflate(layoutInflater, parent, false)

    override fun onViewBindingCreated(binding: ItemErrorBinding) {
        binding.retryButton.setOnClickListener { onRetryClicked() }
    }

    override fun bind(item: ErrorViewModel, binding: ItemErrorBinding) {
        binding.hint.setText(item.errorHintResId)
        binding.retryButton.setText(item.retryButtonTextResId)
    }
}
