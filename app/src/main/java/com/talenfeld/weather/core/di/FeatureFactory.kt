package com.talenfeld.weather.core.di

interface FeatureFactory<Feature: Any> {
    val feature: Feature
}
