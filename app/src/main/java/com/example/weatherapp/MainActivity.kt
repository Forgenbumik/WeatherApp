package com.example.weatherapp

import OpenMeteoAPI
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.util.Clock
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    private val openMeteoAPI = OpenMeteoAPI()

    val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        weatherViewModel.fetchWeather()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isDarkTheme = mutableStateOf(true)
        val isCelsius = mutableStateOf(true)
        val city = mutableStateOf("")
        setContent {
            WeatherAppTheme {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.verticalScroll(ScrollState(0))
                ) {
                    ShowCurrentWeather(isCelsius = isCelsius)
                    ShowHourlyForecast(isCelsius = isCelsius)
                }
            }
        }


    }
}

@Composable
fun City(city: String) {
    Text(city)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowCurrentWeather(viewModel: WeatherViewModel = viewModel(), isCelsius: State<Boolean>) {

    val weather = viewModel.weather.observeAsState().value
    if (weather != null) {
        FlowColumn(
            modifier = Modifier
                .fillMaxHeight(0.4f) // 50% высоты экрана
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Bottom
        ) {
            CurrentTemp(weather.current?.temperature2m, isCelsius)
            WeatherCondition(weather.current?.weatherCode)
            ApparentTemp(weather.current?.apparentTemperature, isCelsius)
            WindCondition(weather.current?.windSpeed10m, weather.current?.windDirection10m)
        }
    }
}

@Composable
fun CurrentTemp(temp: Double?, isCelsius: State<Boolean>) {
    var measure: String
    var temperature: Double
    if (isCelsius.value) {
        measure = "°C"
    }
    else {
        if (temp != null) {
            temperature = temp * 1.8+32
        }
        measure = "°F"
    }
    Text("$temp$measure", fontSize = 28.sp)
}

@Composable
fun WeatherCondition(condition: Int?) {
    Text("$condition")
}

@Composable
fun ApparentTemp(temp: Double?, isCelsius: State<Boolean>) {
    var measure: String
    var temperature: Double
    if (isCelsius.value) {
        measure = "°C"
    }
    else {
        if (temp != null) {
            temperature = temp * 1.8+32
        }
        measure = "°F"
    }
    Text("Ощущается как $temp$measure")
}

@Composable
fun WindCondition(windSpeed: Double?, windDirection: Int?) {
    Text("Ветер: $windSpeed м/с, $windDirection")
}

@Composable
fun Humidity(humidity: Int) {
    Text("Влажность: $humidity%")
}

@Composable
fun ShowHourlyForecast(viewModel: WeatherViewModel = viewModel(), isCelsius: State<Boolean>) {
    val weather = viewModel.hourlyForecast.value
    if (weather != null) {
        Column(
            Modifier.horizontalScroll(ScrollState(0))
        ) {
            for (i in 0..23) {
                //HourWeather(weather.hourly[i].temperature2m[i], weather.hourly[i].weatherCode[i])
            }
        }
    }
}

@Composable
fun HourWeather(temp: Double, isCelsius: State<Boolean>, weatherCondition: Int) {
    var measure: String
    var temperature: Double
    if (isCelsius.value) {
        measure = "°C"
    }
    else {
        if (temp != null) {
            temperature = temp * 1.8+32
        }
        measure = "°F"
    }
    Column {
        Text("$weatherCondition")
        Text("$temp°")
    }
}

@Composable
fun ShowDailyForecast(viewModel: WeatherViewModel = viewModel(), isCelsius: State<Boolean>) {

    val days = mapOf(0 to "Понедельник", 1 to "Вторник", 2 to "Среда", 3 to "Четверг",
        4 to "Пятница", 5 to "Суббота", 6 to "Воскресенье")
    val today = LocalDate.now().dayOfWeek
    val people = mapOf(1 to "Понедельник", 5 to "Sam", 8 to "Bob")
    val weather = viewModel.dailyForecast.value
    if (weather != null) {
        Column {
            for (i in 0..6) {
                //DailyWeather(days[i], weather.temperature2mMax)
            }
        }
    }
}

@Composable
fun DailyWeather(Day: String, maxTemp: Double, minTemp: Double, weatherCondition: Int) {
    Row {
        Text(Day)
        Text("$maxTemp°")
        Text("$minTemp°")
        Text("$weatherCondition")
    }

}