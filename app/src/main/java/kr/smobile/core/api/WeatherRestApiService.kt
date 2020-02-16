package kr.smobile.core.api

import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenWeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherRestApiService {

    companion object {
        const val version : String = "2.5"
        const val appid : String = "33b32527d112f0369317d151b37a1582"
    }

    /**
     * by city name
     * api.openweathermap.org/data/2.5/weather?q={city name}
     * api.openweathermap.org/data/2.5/weather?q={city name},{country code}
     */

    @GET("weather?appid=$appid&units=metric")
    fun getWeatherByCityName( @Query("q") cityName: String ) : Flowable<OpenWeatherResult>

    /**
     * by city id
     *
     */
    @GET("weather?appid=$appid&units=metric")
    fun getWeatherByCityId( @Query("id") cityId: Int ) : Flowable<OpenWeatherResult>

    @GET("group?appid=$appid&units=metric")
    fun getWeatherByCityIds( @Query("ids") cityId: String, @Query("units") units: String ) : Flowable<OpenWeatherResult>

    /**
     * by cooridinates
     */
    @GET("weather?appid=$appid&units=metric")
    fun getWeatherByCoordinate( @Query("lat") lat: Double, @Query("lon") lon : Double ) : Flowable<OpenWeatherResult>

    /**
     * by zip code
     * api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}
     */
    @GET("weather?appid=$appid&units=metric&zip={zipCode},{contryCode}")
    fun getWeatherByZipCode( @Path("zipCode") zipCode: String, @Path("contryCode") contryCode: String ) : Flowable<OpenWeatherResult>


    //forecast
    //Call 5 day / 3 hour forecast data
    @GET("forecast?appid=$appid&units=metric")
    fun getForecastByCityName( @Query("q") cityName: String) : Flowable<ForeCastResult>

    @GET("forecast?appid=$appid&units=metric")
    fun getForecastByCityId( @Query("id") cityId: Int) : Flowable<ForeCastResult>




}