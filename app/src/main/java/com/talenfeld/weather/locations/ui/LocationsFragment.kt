package com.talenfeld.weather.locations.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.talenfeld.weather.core.di.components
import com.talenfeld.weather.core.feature.bindFeature
import com.talenfeld.weather.core.ui.adapter.ButtonAdapter
import com.talenfeld.weather.core.ui.adapter.LoadingAdapter
import com.talenfeld.weather.core.ui.adapter.TitleAdapter
import com.talenfeld.weather.core.ui.adapter.base.DiffAdapter
import com.talenfeld.weather.databinding.FragmentLocationsBinding
import com.talenfeld.weather.locations.di.LocationsFactory
import com.talenfeld.weather.locations.feature.Locations
import com.talenfeld.weather.locations.ui.adapter.LocationAdapter

class LocationsFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    private val featureFactory: LocationsFactory by lazy { components.locationsRef() }
    private val feature by lazy { featureFactory.feature }

    private val diffAdapter = DiffAdapter(
        LoadingAdapter(),
        LocationAdapter(),
        TitleAdapter(),
        ButtonAdapter({})
    )

    init {
        bindFeature(feature, ::renderState, {})
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler(binding.content)
    }

    private fun renderState(state: Locations.State) {
        val viewModel = featureFactory.viewModelFactory.create(state)
        diffAdapter.swapData(viewModel.items)
    }

    private fun setupRecycler(recyclerView: RecyclerView): Unit = with(recyclerView) {
        adapter = diffAdapter
        layoutManager = LinearLayoutManager(context)
    }
}
