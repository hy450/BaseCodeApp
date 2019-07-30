package kr.smobile.core.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.smobile.vo.OpenForeCastWeather
import kr.smobile.vo.WeatherItem

object OpenWeatherTypeConverter {

    @JvmStatic
    @TypeConverter
    fun weatherItemToGsonStr( value: List<WeatherItem>?) : String {
        if(value == null) return ""

        val gson = Gson()
        return try { gson.toJson(value) } catch (e: Exception) { "" }

    }

    @JvmStatic
    @TypeConverter
    fun gsonStrToweatherItem( value: String) : List<WeatherItem>? {
        if(value.isEmpty()) return null

        val gson= Gson()
        val listType = object : TypeToken<List<WeatherItem>>(){}.type
        return try { gson.fromJson(value,listType) } catch (e: Exception) { null }
    }

    @JvmStatic
    @TypeConverter
    fun gsonStrOpenForeCastWeather( value: String ) : List<OpenForeCastWeather>? {
        if(value.isEmpty()) return null

        val gson= Gson()
        val listType = object : TypeToken<List<OpenForeCastWeather>>(){}.type
        return try { gson.fromJson(value,listType) } catch (e: Exception) { null }
    }

    @JvmStatic
    @TypeConverter
    fun foreCastWeatherToGsonStr( value: List<OpenForeCastWeather>? ) : String {
        if(value == null) return ""

        val gson = Gson()
        return try { gson.toJson(value) } catch (e: Exception) { "" }
    }

}