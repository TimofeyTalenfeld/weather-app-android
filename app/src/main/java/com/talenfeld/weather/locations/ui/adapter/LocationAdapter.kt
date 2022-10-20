package com.talenfeld.weather.locations.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.core.util.applyOrInvisible
import com.talenfeld.weather.databinding.ItemLocationBinding

data class LocationViewModel(
    val locationName: String,
    val conditionIconResId: Int?,
    val temperature: String,
): ComparableItem {
    override fun id(): Any = locationName

    override fun content(): Any = this
}

class LocationAdapter: SimpleDelegateAdapter<LocationViewModel, ItemLocationBinding>(LocationViewModel::class.java) {

    override fun createViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemLocationBinding = ItemLocationBinding.inflate(layoutInflater, parent, false)

    override fun bind(item: LocationViewModel, binding: ItemLocationBinding) = with (binding) {
        location.text = item.locationName
        conditionIcon.applyOrInvisible(item.conditionIconResId, ImageView::setImageResource)
        currentTemperature.text = item.temperature
    }
}
