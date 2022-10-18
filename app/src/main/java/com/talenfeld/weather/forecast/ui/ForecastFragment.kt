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
import com.google.android.material.appbar.MaterialToolbar
import com.talenfeld.weather.core.di.components
import com.talenfeld.weather.core.feature.bindFeature
import com.talenfeld.weather.databinding.FragmentForecastBinding
import com.talenfeld.weather.forecast.di.ForecastFactory
import com.talenfeld.weather.forecast.feature.Forecast

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null

    private val binding get() = _binding!!

    private val featureFactory: ForecastFactory by lazy { components.forecastRef() }

    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted: Boolean ->
            featureFactory.feature.accept(Forecast.Msg.OnLocationPermissionResult(granted))
        }

    init {
        bindFeature(featureFactory.feature, ::renderState, ::handleEffect)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: Forecast.State) {

    }

    private fun handleEffect(eff: Forecast.Eff) = when (eff) {
        is Forecast.Eff.CheckLocationPermission -> requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        else -> Unit
    }

    private fun requestPermission(permission: String) {
        val granted = ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
        when {
            granted -> {
                featureFactory.feature.accept(Forecast.Msg.OnLocationPermissionResult(true))
            }
            else -> requestLocationPermissionLauncher.launch(permission)
        }
    }

    private fun setupToolbar(toolbar: MaterialToolbar) {
        toolbar.setNavigationOnClickListener {
        }
    }
}
