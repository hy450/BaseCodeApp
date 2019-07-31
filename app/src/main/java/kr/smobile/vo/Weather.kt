package kr.smobile.vo

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kr.smobile.core.db.OpenWeatherTypeConverter
import java.util.*

data class WeatherItem(
    @field:SerializedName("id")
    val id: Int, // Weather condition id // https://openweathermap.org/weather-conditions
    @field:SerializedName("main")
    val main: String, // weather parametr  ex> Rain, Snow, Extreme, Clear
    @field:SerializedName("description")
    val description: String, // Weather condition within the group
    @field:SerializedName("icon")
    val icon:String
)

data class Coordinate (

    @field:SerializedName("lat")
    val latitude: Double,
    @field:SerializedName("lon")
    val longitude: Double

)

data class MainInfo(
    @field:SerializedName("temp")
    val temperature: Double,
    @field:SerializedName("pressure")
    val atmosphericPressure: Double,
    @field:SerializedName("temp_min")
    val temp_min : Double,
    @field:SerializedName("temp_max")
    val temp_max: Double,
    @field:SerializedName("humidity") // 습도 unit %
    val humidity: Int


)

@Entity(primaryKeys = ["coord_latitude","coord_longitude", "datetime"])
@TypeConverters(OpenWeatherTypeConverter::class)
data class OpenWeatherResult(
    @field:SerializedName("coord")
    @field:Embedded( prefix = "coord_")
    val coordinate: Coordinate,
    @field:SerializedName("weather")
    val weatherItems: List<WeatherItem>,

    @field:SerializedName("main")
    @field:Embedded
    val mainWeatherInfo: MainInfo,

    @field:SerializedName("id")
    val cityId: Int,
    @field:SerializedName("name")
    val cityName: String,
    @field:SerializedName("dt") // Time of data calculation, unix, UTC
    val datetime: Long,
    @field:SerializedName("timezone")
    val timezone: Int?,
    @field:SerializedName("cod") // internal parameter
    val cod: Int

)


data class OpenForeCastWeather(
    @field:SerializedName("weather")
    val weatherItems: List<WeatherItem>,
    @field:SerializedName("main")
    val mainWeatherInfo: MainInfo,
    @field:SerializedName("dt") // Time of data calculation, unix, UTC
    val datetime: Long,
    @field:SerializedName("dt_txt")
    val dt_txt: String?
)

data class OpenForeCastCity(
    @field:SerializedName("coord")
    @field:Embedded( prefix = "coord_")
    val coordinate: Coordinate,
    @field:SerializedName("id")
    val cityId: Int,
    @field:SerializedName("name")
    val cityName: String
)

//forecast result
@Entity(primaryKeys = ["coord_latitude","coord_longitude", "createAt"])
@TypeConverters(OpenWeatherTypeConverter::class)
data class ForeCastResult(
    @field:SerializedName("cod")       // internal parameter
    val cod: Int,
    @field:SerializedName("cnt")
    val count: Int,

    @field:SerializedName("list")
    val list: List<OpenForeCastWeather>,

    @field:SerializedName("city")
    @field:Embedded
    val cityInfo: OpenForeCastCity,
    var createAt: Long = 0                      // db 생성 날짜

)