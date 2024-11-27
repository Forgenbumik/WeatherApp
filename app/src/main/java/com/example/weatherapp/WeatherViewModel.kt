package com.example.weatherapp

import OpenMeteoAPI
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _currentweather = MutableLiveData<CurrentWeather?>()
    val weather: LiveData<CurrentWeather?> = _currentweather

    private val _hourlyforecast = MutableLiveData<HourlyForecast>()
    val hourlyForecast: LiveData<HourlyForecast?> = _hourlyforecast

    private val _dailyforecast = MutableLiveData<Daily>()
    val dailyForecast: LiveData<Daily?> = _dailyforecast
    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val openMeteoAPI = OpenMeteoAPI()
            val currentWeather = openMeteoAPI.getCurrentWeather(latitude, longitude)
            val hourlyForecast = openMeteoAPI.getHourlyForecast(latitude, longitude)
            _currentweather.postValue(currentWeather) // Обновляем данные
        }
    }
}