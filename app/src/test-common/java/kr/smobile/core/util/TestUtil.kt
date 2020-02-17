package kr.smobile.core.util

import com.google.gson.Gson
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenWeatherResult
import java.io.InputStreamReader

object TestUtil {

    fun createWeather() : OpenWeatherResult {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("weatherresponse.json")

        val gson = Gson()
        val openWeatherResult = gson.fromJson(InputStreamReader(inputStream,"UTF-8"), OpenWeatherResult::class.java)
        return openWeatherResult

    }

    fun createWeather1() : OpenWeatherResult {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("weatherresponse1.json")

        val gson = Gson()
        val openWeatherResult = gson.fromJson(InputStreamReader(inputStream,"UTF-8"), OpenWeatherResult::class.java)
        return openWeatherResult

    }

    fun createForecastWeather() : ForeCastResult {
        val inputstream = javaClass.classLoader.getResourceAsStream("forecastresponse.json")

        val gson = Gson()
        val foreCastResult = gson.fromJson( InputStreamReader(inputstream, "UTF-8"),ForeCastResult::class.java )
        return foreCastResult
    }
}