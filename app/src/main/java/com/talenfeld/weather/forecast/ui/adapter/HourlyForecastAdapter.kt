package com.talenfeld.weather.forecast.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.databinding.ItemHourlyForecastBinding

data class HourlyForecastViewModel(
    val time: String,
    val conditionIconResId: Int,
    val temperature: String,
): ComparableItem {
    override fun id(): Any = time

    override fun content(): Any = this
}

class HourlyForecastAdapter: SimpleDelegateAdapter<HourlyForecastViewModel, ItemHourlyForecastBinding>(HourlyForecastViewModel::class.java) {

    override fun createViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemHourlyForecastBinding = ItemHourlyForecastBinding.inflate(layoutInflater, parent, false)

    override fun bind(item: HourlyForecastViewModel, binding: ItemHourlyForecastBinding) = with (binding) {
        time.text = item.time
        conditionIcon.setImageResource(item.conditionIconResId)
        temperature.text = item.temperature
    }
}
