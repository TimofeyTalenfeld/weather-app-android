package com.talenfeld.weather.forecast.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.databinding.ItemDailyForecastBinding

data class DailyForecastViewModel(
    val day: String,
    val conditionIconResId: Int,
    val temperature: String,
): ComparableItem {
    override fun id(): Any = day

    override fun content(): Any = this
}

class DailyForecastAdapter: SimpleDelegateAdapter<DailyForecastViewModel, ItemDailyForecastBinding>(DailyForecastViewModel::class.java) {

    override fun createViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemDailyForecastBinding = ItemDailyForecastBinding.inflate(layoutInflater, parent, false)

    override fun bind(item: DailyForecastViewModel, binding: ItemDailyForecastBinding) = with (binding) {
        dayOfWeek.text = item.day
        conditionIcon.setImageResource(item.conditionIconResId)
        temperature.text = item.temperature
    }
}
