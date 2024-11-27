package com.example.weatherapp

import OpenMeteoAPI
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    private val openMeteoAPI = OpenMeteoAPI()

    val weatherViewModel: WeatherViewModel by viewModels()

    private lateinit var locationViewer: LocationViewer

    override fun onCreate(savedInstanceState: Bundle?) {

        val isDarkTheme = mutableStateOf(true)
        val isCelsius = mutableStateOf(true)
        val city = mutableStateOf("")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    showCurrentWeather(weatherViewModel)
                }
            }
        }

        locationViewer = LocationViewer(this)
        locationViewer.getLastKnownLocation(object : LocationCallback {
            override fun onLocationReceived(latitude: Double, longitude: Double) {
                weatherViewModel.fetchWeather(latitude, longitude)
                }
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting("Android")
    }
}

@Composable
fun City(city: String) {
    Text(city)
}

@Composable
fun showCurrentWeather(viewModel: WeatherViewModel = viewModel()) {

    val weather = viewModel.weather.observeAsState().value
    if (weather != null) {
        currentTemp(weather.current?.temperature2m, measure)
        weatherCondition(weather.current?.weatherCode)
        apparentTemp(apparentTemp, measure)
    }
}

@Composable
fun currentTemp(temp: Double?, isCelsius: Recomposer.State<Boolean>) {
    Text("$temp", fontSize = 28.sp)
}

@Composable
fun weatherCondition(condition: Int?) {
    Text("$condition")
}

@Composable
fun apparentTemp(temp: Int, measure: String) {
    Text("Ощущается как $temp°$measure")
}

@Composable
fun windCondition(windSpeed: Int, windDirection: String) {
    Text("Ветер: $windSpeed м/с, $windDirection")
}

@Composable
fun humidity(humidity: Int) {
    Text("Влажность: $humidity%")
}

@Composable
fun showHourlyForecast() {

}