package com.talenfeld.weather.forecast.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.talenfeld.weather.R
import com.talenfeld.weather.core.di.components
import com.talenfeld.weather.core.feature.bindFeature
import com.talenfeld.weather.core.ui.adapter.ErrorAdapter
import com.talenfeld.weather.core.ui.adapter.GalleryAdapter
import com.talenfeld.weather.core.ui.adapter.LoadingAdapter
import com.talenfeld.weather.core.ui.adapter.base.DiffAdapter
import com.talenfeld.weather.databinding.FragmentForecastBinding
import com.talenfeld.weather.forecast.di.ForecastFactory
import com.talenfeld.weather.forecast.feature.Forecast
import com.talenfeld.weather.forecast.ui.adapter.DailyForecastAdapter
import com.talenfeld.weather.forecast.ui.adapter.HourlyForecastAdapter
import com.talenfeld.weather.forecast.ui.adapter.LocationCardAdapter

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null

    private val binding get() = _binding!!

    private val featureFactory: ForecastFactory by lazy { components.forecastRef() }
    private val feature by lazy { featureFactory.feature }

    private val diffAdapter = DiffAdapter(
        LoadingAdapter(),
        ErrorAdapter(onRetryClicked = { feature.accept(Forecast.Msg.OnErrorRetryClicked) }),
        LocationCardAdapter(),
        GalleryAdapter(
            delegateAdapter = HourlyForecastAdapter(),
            horizontalPaddingResId = R.dimen.default_half_padding
        ),
        DailyForecastAdapter()
    )

    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted: Boolean ->
            val grantStatus = when {
                granted -> {
                    Forecast.Msg.OnLocationPermissionResult.GrantStatus.GRANTED
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION).not() -> {
                    Forecast.Msg.OnLocationPermissionResult.GrantStatus.REQUEST_DENIED
                }
                else -> {
                    Forecast.Msg.OnLocationPermissionResult.GrantStatus.DENIED
                }
            }
            feature.accept(Forecast.Msg.OnLocationPermissionResult(grantStatus))
        }

    init {
        bindFeature(feature, ::renderState, ::handleEffect)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)
        setupRecycler(binding.list)
    }

    override fun onStart() {
        super.onStart()
        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: Forecast.State) {
        val viewModel = featureFactory.viewModelFactory.create(state)
        diffAdapter.swapData(viewModel.listItems)
    }

    private fun handleEffect(eff: Forecast.Eff) = when (eff) {
        else -> Unit
    }

    private fun requestPermission(permission: String) {
        val granted = ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
        when {
            granted -> {
                feature.accept(
                    Forecast.Msg.OnLocationPermissionResult(
                        Forecast.Msg.OnLocationPermissionResult.GrantStatus.GRANTED
                    )
                )
            }
            else -> {
                requestLocationPermissionLauncher.launch(permission)
            }
        }
    }

    private fun setupToolbar(toolbar: MaterialToolbar) {
        toolbar.setNavigationOnClickListener {
        }
    }

    private fun setupRecycler(recyclerView: RecyclerView): Unit = with(recyclerView) {
        adapter = diffAdapter
        layoutManager = LinearLayoutManager(context)
    }
}
