package com.example.weatherapp

import com.google.gson.annotations.SerializedName

data class CurrentResponse (

    @SerializedName("time"                 ) var time                : String? = null,
    @SerializedName("interval"             ) var interval            : Int?    = null,
    @SerializedName("temperature_2m"       ) var temperature2m       : Double? = null,
    @SerializedName("relative_humidity_2m" ) var relativeHumidity2m  : Int?    = null,
    @SerializedName("apparent_temperature" ) var apparentTemperature : Double? = null,
    @SerializedName("precipitation"        ) var precipitation       : Int?    = null,
    @SerializedName("weather_code"         ) var weatherCode         : Int?    = null,
    @SerializedName("wind_speed_10m"       ) var windSpeed10m        : Double? = null,
    @SerializedName("wind_direction_10m"   ) var windDirection10m    : Int?    = null
)