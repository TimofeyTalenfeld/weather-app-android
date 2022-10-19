package com.talenfeld.weather.forecast.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.databinding.ItemLocationCardBinding

data class LocationCardViewModel(
    val locationName: String,
    val temperatureText: String
): ComparableItem {
    override fun id(): Any = "LocationCardViewModel"

    override fun content(): Any = this
}

class LocationCardAdapter: SimpleDelegateAdapter<LocationCardViewModel, ItemLocationCardBinding>(LocationCardViewModel::class.java) {
    override fun createViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemLocationCardBinding = ItemLocationCardBinding.inflate(layoutInflater, parent, false)

    override fun bind(item: LocationCardViewModel, binding: ItemLocationCardBinding) {
        binding.locationName.text = item.locationName
        binding.temperature.text = item.temperatureText
    }
}
