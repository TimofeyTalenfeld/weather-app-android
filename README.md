# weather-app-android

## Description
Simple app that shows weather forecast.

<img src="https://github.com/TimofeyTalenfeld/weather-app-android/blob/main/assets/forecast_screen.png" width="200"/> <img src="https://github.com/TimofeyTalenfeld/weather-app-android/blob/main/assets/locations_screen.png" width="200"/>

Download [apk](https://github.com/TimofeyTalenfeld/weather-app-android/raw/main/assets/app-release.apk)

## Technology

**Architecture**

Used MVI architecture called TEA (the Elm Architecture). It's simple MVI realization based on state machine behavior.

**Network**

Used Retrofit + KotlinX Serialization

**DI**

Used self-written components, as TEA suppose you to create components on short way and side frameworks are not needed.
