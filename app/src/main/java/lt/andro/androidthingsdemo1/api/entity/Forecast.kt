package lt.andro.androidthingsdemo1.api.entity

import com.google.gson.annotations.SerializedName

data class Forecast(
        val cod: String?,
        val message: Double?,
        val cnt: Int?,
        val list: List<ForecastItem>?,
        val city: City?
)

data class City(
        val id: Int?,
        val name: String?,
        val coord: LatLon?,
        val country: String?
)

data class LatLon(val lat: Double?, val lon: Double?)

data class ForecastItem(
        val dt: Long?, // TODO convert to Date
        val main: Main?,
        val weather: List<Weather>?,
        val wind: Wind?,
        val rain: Rain?,
        val dt_txt: String?
)

data class Rain(@SerializedName("3h") val period3h: Double?)

data class Main(
        val temp: Double?,
        val temp_min: Double?,
        val temp_max: Double?,
        val pressure: Double?,
        val sea_level: Double?,
        val grnd_level: Double?,
        val humidity: Double?
)

data class Weather(
        val id: Int?,
        val main: String?, //"Rain"
        val description: String?//"light rain"
)

data class Wind(val speed: Double?, val deg: Double?)