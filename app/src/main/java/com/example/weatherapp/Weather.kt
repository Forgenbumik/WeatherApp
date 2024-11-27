package com.example.weatherapp

import com.google.gson.annotations.SerializedName

data class CurrentWeather (

    @SerializedName("latitude"  ) var latitude  : Double?  = null,
    @SerializedName("longitude" ) var longitude : Double?  = null,
    @SerializedName("current"   ) var current   : CurrentResponse? = CurrentResponse()
)

data class DailyforCurrent (

    @SerializedName("time"   ) var time   : String? = null,
    @SerializedName("sunrise") var sunrise: String? = null,
    @SerializedName("sunset" ) var sunset : String? = null
)

data class Daily (

    @SerializedName("time"               ) var time             : ArrayList<String> = arrayListOf(),
    @SerializedName("weather_code"       ) var weatherCode      : ArrayList<Int>    = arrayListOf(),
    @SerializedName("temperature_2m_max" ) var temperature2mMax : ArrayList<Double> = arrayListOf(),
    @SerializedName("temperature_2m_min" ) var temperature2mMin : ArrayList<Double> = arrayListOf()

)

data class DailyForecast (

    @SerializedName("daily") var daily : ArrayList<Daily>? = arrayListOf()

)

data class Hourly (

    @SerializedName("time"                ) var time               : ArrayList<String> = arrayListOf(),
    @SerializedName("temperature_2m"      ) var temperature2m      : ArrayList<Double> = arrayListOf(),
    @SerializedName("relative_humidity_2m") var relativeHumidity2m : ArrayList<Int>    = arrayListOf(),
    @SerializedName("apparent_temperature") var apparentTemperature: ArrayList<Int>    = arrayListOf(),
    @SerializedName("weather_code"        ) var weatherCode        : ArrayList<Int>    = arrayListOf(),
    @SerializedName("wind_speed_10m"      ) var windSpeed10m       : ArrayList<Int>    = arrayListOf(),
    @SerializedName("wind_direction_10m"  ) var windDirection10m   : ArrayList<Int>    = arrayListOf()
)

data class HourlyForecast(
    @SerializedName("hourly") var hourly: ArrayList<Hourly> = arrayListOf()
)