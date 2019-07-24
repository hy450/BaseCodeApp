package kr.smobile.core.api

import androidx.lifecycle.LiveData
import kr.smobile.vo.OpenWeatherResult
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

    @GET("weather?appid=$appid")
    fun getWeatherByCityName( @Query("q") cityName: String ) : LiveData<ApiResponse<OpenWeatherResult>>

    /**
     * by city id
     *
     */
    @GET("weather?appid=$appid")
    fun getWeatherByCityId( @Query("id") cityId: String ) : LiveData<ApiResponse<OpenWeatherResult>>

    @GET("group?appid=$appid")
    fun getWeatherByCityIds( @Query("ids") cityId: String, @Query("units") units: String ) : LiveData<ApiResponse<OpenWeatherResult>>

    /**
     * by cooridinates
     */
    @GET("weather?appid=$appid")
    fun getWeatherByCoordinate( @Query("lat") lat: Double, @Query("lon") lon : Double ) : LiveData<ApiResponse<OpenWeatherResult>>

    /**
     * by zip code
     * api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}
     */
    @GET("weather?appid=$appid&zip={zipCode},{contryCode}")
    fun getWeatherByZipCode( @Path("zipCode") zipCode: String, @Path("contryCode") contryCode: String ) : LiveData<ApiResponse<OpenWeatherResult>>




}