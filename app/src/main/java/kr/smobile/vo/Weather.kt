package kr.smobile.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kr.smobile.core.db.OpenWeatherTypeConverter
import java.util.*

data class WeatherItem(
    @field:SerializedName("id")
    val id: Int, // Weather condition id
    @field:SerializedName("main")
    val main: String, // weather parametr  ex> Rain, Snow, Extreme, Clear
    @field:SerializedName("description")
    val description: String, // Weather condition within the group
    @field:SerializedName("icon")
    val icon:String
)

data class Coordindate (

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

@Entity(primaryKeys = ["id"])
@TypeConverters(OpenWeatherTypeConverter::class)
data class OpenWeatherResult(
    @field:SerializedName("id")
    var id: Long,
    @field:SerializedName("coord")
    val coordindate: Coordindate,
    @field:SerializedName("weather")
    val weatherItems: List<WeatherItem>,
    @field:SerializedName("main")
    val mainWeatherInfo: MainInfo,
    @field:SerializedName("id")
    val cityId: Int,
    @field:SerializedName("name")
    val cityName: String,
    @field:SerializedName("dt") // Time of data calculation, unix, UTC
    val date: Date,
    @field:SerializedName("timezone")
    val timezone: Int,
    @field:SerializedName("cod") // internal parameter
    val cod: Int

)