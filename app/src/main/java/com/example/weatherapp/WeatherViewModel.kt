package com.example.weatherapp

import OpenMeteoAPI
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _locationviewer = MutableLiveData<LocationViewer>()
    val locationViewer: LiveData<LocationViewer?> = _locationviewer

    private val _currentweather = MutableLiveData<CurrentWeather?>()
    val weather: LiveData<CurrentWeather?> = _currentweather

    private val _hourlyforecast = MutableLiveData<HourlyForecast?>()
    val hourlyForecast: LiveData<HourlyForecast?> = _hourlyforecast

    private val _dailyforecast = MutableLiveData<Daily>()
    val dailyForecast: LiveData<Daily?> = _dailyforecast
    val openMeteoAPI = OpenMeteoAPI()

    fun fetchWeather() {
        viewModelScope.launch {

            locationViewer.value?.getLastKnownLocation()
            val currentWeather = locationViewer.value?.let {
                openMeteoAPI.getCurrentWeather(
                    it.latitude, it.longitude)
            }
            val hourlyForecast = locationViewer.value?.let {
                openMeteoAPI.getHourlyForecast(
                    it.latitude, it.longitude)
            }
            _currentweather.postValue(currentWeather) // Обновляем данные
            _hourlyforecast.postValue(hourlyForecast)
        }
    }

    private fun startWeatherUpdateLoop() {
        viewModelScope.launch {
            while (true) {
                fetchWeather() // Пример координат
                delay(30 * 60 * 1000L) // 30 минут в миллисекундах
            }
        }
    }
}