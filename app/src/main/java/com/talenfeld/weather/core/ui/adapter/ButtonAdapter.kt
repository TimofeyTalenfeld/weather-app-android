package com.talenfeld.weather.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.databinding.ItemButtonBinding

data class ButtonViewModel(
    val id: String,
    val text: String
): ComparableItem {
    override fun id(): Any = id

    override fun content(): Any = this
}

class ButtonAdapter(
    private val onClicked: (ButtonViewModel) -> Unit
): SimpleDelegateAdapter<ButtonViewModel, ItemButtonBinding>(ButtonViewModel::class.java) {
    override fun createViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemButtonBinding = ItemButtonBinding.inflate(layoutInflater, parent, false)

    override fun bind(item: ButtonViewModel, binding: ItemButtonBinding) {
        binding.button.run {
            text = item.text
            setOnClickListener { onClicked(item) }
        }
    }
}
