package com.talenfeld.weather.main.data.repository

import com.talenfeld.weather.main.data.api.ForecastApi
import com.talenfeld.weather.main.data.model.*
import io.reactivex.Single

interface IForecastRepository {
    fun getForecast(region: Location): Single<ForecastCompilation>
}

class ForecastRepository(
    private val api: ForecastApi
): IForecastRepository {
    override fun getForecast(location: Location): Single<ForecastCompilation> =
        api.getForecast(location.lat, location.lon).map(ForecastConverter::convert)
}

private object ForecastConverter {
    fun convert(src: ForecastResult): ForecastCompilation = ForecastCompilation(
        currentWeather = WeatherConverter.convert(src.currentWeather),
        hourlyForecast = WeatherConverter.convert(src.hourly),
        dailyForecast = WeatherConverter.convert(src.daily)
    )
}

private object WeatherConverter {
    fun convert(src: CurrentWeather): Weather.AtMoment =
        Weather.AtMoment(
            isoTime = src.time,
            condition = WeatherCodeConverter.convert(src.weatherCode),
            temperature = src.temperature
        )

    fun convert(src: HourlyForecast): List<Weather.AtMoment> =
        minOf(src.time.size, src.temperature.size, src.weatherCode.size)
            .let { 0 until it }
            .map { index: Int ->
                Weather.AtMoment(
                    isoTime = src.time[index],
                    condition = WeatherCodeConverter.convert(src.weatherCode[index]),
                    temperature = src.temperature[index]
                )
        }.toList()

    fun convert(src: DailyForecast): List<Weather.Daily> =
        minOf(
            src.time.size,
            src.temperatureMin.size,
            src.temperatureMax.size,
            src.weatherCode.size
        )
            .let { 0 until it }
            .map { index: Int ->
                Weather.Daily(
                    isoTime = src.time[index],
                    condition = WeatherCodeConverter.convert(src.weatherCode[index]),
                    temperatureMin = src.temperatureMin[index],
                    temperatureMax = src.temperatureMax[index]
                )
            }.toList()
}

private object WeatherCodeConverter {
    fun convert(code: Int): Condition = when (code) {
        0 -> Condition.CLEAR
        1, 2, 3 -> Condition.CLOUDY
        // other weather codes in further releases
        else -> Condition.RAINY
    }
}
