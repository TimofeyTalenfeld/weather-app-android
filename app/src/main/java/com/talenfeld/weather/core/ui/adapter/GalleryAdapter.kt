package com.talenfeld.weather.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.base.DelegateAdapter
import com.talenfeld.weather.core.ui.adapter.base.DiffAdapter
import com.talenfeld.weather.core.ui.adapter.base.SimpleDelegateAdapter
import com.talenfeld.weather.databinding.ItemGalleryBinding

data class GalleryViewModel(
    val id: String,
    val items: List<ComparableItem>
): ComparableItem {
    override fun id(): Any = id

    override fun content(): Any = this
}

class GalleryAdapter(
    delegateAdapter: DelegateAdapter,
    private val horizontalPaddingResId: Int
): SimpleDelegateAdapter<GalleryViewModel, ItemGalleryBinding>(GalleryViewModel::class.java) {

    private val diffAdapter = DiffAdapter(delegateAdapter)

    override fun createViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup): ItemGalleryBinding =
        ItemGalleryBinding.inflate(layoutInflater, parent, false)

    override fun onViewBindingCreated(binding: ItemGalleryBinding) {
        binding.galleryList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = diffAdapter

            val horizontalPaddingPx = resources.getDimension(horizontalPaddingResId).toInt()
            setPadding(horizontalPaddingPx, 0, horizontalPaddingPx, 0)
            clipToPadding = false
        }
    }

    override fun bind(item: GalleryViewModel, binding: ItemGalleryBinding) {
        diffAdapter.swapData(item.items)
    }
}
