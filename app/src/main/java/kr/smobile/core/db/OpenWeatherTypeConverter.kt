package kr.smobile.core.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.smobile.vo.Coordindate
import kr.smobile.vo.MainInfo
import kr.smobile.vo.WeatherItem
import java.util.*

object OpenWeatherTypeConverter {

    @JvmStatic
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @JvmStatic
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

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
    fun coordinateToGsonStr( value: Coordindate?) : String {
        if(value == null) return ""

        val gson = Gson()
        return try { gson.toJson(value) } catch (e: Exception) { "" }

    }

    @JvmStatic
    @TypeConverter
    fun gsonStrToCoordinate( value: String) : Coordindate? {
        if(value.isEmpty()) return null

        val gson= Gson()
        return try { gson.fromJson(value,Coordindate::class.java) } catch (e: Exception) { null }
    }

    @JvmStatic
    @TypeConverter
    fun mainInfoToGsonStr( value: MainInfo?) : String {
        if(value == null) return ""

        val gson = Gson()
        return try { gson.toJson(value) } catch (e: Exception) { "" }

    }

    @JvmStatic
    @TypeConverter
    fun gsonStrToMainInfo( value: String) : MainInfo? {
        if(value.isEmpty()) return null

        val gson= Gson()
        return try { gson.fromJson(value,MainInfo::class.java) } catch (e: Exception) { null }
    }


}