import android.util.Log
import com.example.weatherapp.CurrentWeather
import com.example.weatherapp.HourlyForecast
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor

class OpenMeteoAPI(
    private val baseUrl: String = "https://api.open-meteo.com/v1/forecast",
    val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    },

    val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
) {
    private val gson = Gson()

    //Метод для построения URL с параметрами запроса.
    private fun buildUrl(params: Map<String, String>): String {
        val paramString = params.entries.joinToString("&") { "${it.key}=${it.value}" }
        return "$baseUrl/?$paramString"
    }
    private fun buildRequest(url: String): Request {
        val request = Request.Builder()
            .url(url)
            .build()
        return request
    }

    //Получение текущей погоды по широте и долготе.
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeather? {
        val params = mapOf(
            "latitude" to latitude.toString(),
            "longitude" to longitude.toString(),
            "current" to "temperature_2m,relative_humidity_2m,apparent_temperature,precipitation_probability,weather_code,cloud_cover,wind_speed_10m,wind_direction_10m",
            "forecast_days" to "1"
        )

        var url = buildUrl(params)
        var request = buildRequest(url)
        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()?.let { jsonString ->
                    gson.fromJson(jsonString, CurrentWeather::class.java)
                }
            } else {
                null
            }
        }
    }
    suspend fun getHourlyForecast(latitude: Double, longitude: Double): HourlyForecast? {
        val params = mapOf(
            "latitude" to latitude.toString(),
            "longitude" to longitude.toString(),
            "hourly" to "temperature_2m,weather_code,cloud_cover",
            "forecast_days" to "1"
        )

        val url = buildUrl(params)
        val request = buildRequest(url)

        return withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    response.body?.string()?.let { jsonString ->
                        gson.fromJson(responseBody, HourlyForecast::class.java)
                    }
                } else {
                    null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}
