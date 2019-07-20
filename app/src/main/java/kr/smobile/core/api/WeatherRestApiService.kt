package kr.smobile.core.api

import androidx.lifecycle.LiveData
import kr.smobile.vo.OpenWeatherResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherRestApiService {

    companion object {
        const val version : String = "2.5"
    }

    /**
     * by city name
     * api.openweathermap.org/data/2.5/weather?q={city name}
     * api.openweathermap.org/data/2.5/weather?q={city name},{country code}
     */

    @GET("weather")
    fun getWeatherByCityName( @Query("q") cityName: String ) : LiveData<OpenWeatherResult>

    /**
     * by city id
     *
     */
    @GET("weather")
    fun getWeatherByCityId( @Query("id") cityId: String ) : LiveData<OpenWeatherResult>

    @GET("group")
    fun getWeatherByCityIds( @Query("ids") cityId: String, @Query("units") units: String ) : LiveData<OpenWeatherResult>

    /**
     * by cooridinates
     */
    @GET("weather")
    fun getWeatherByCoordinate( @Query("lat") lat: Double, @Query("lon") lon : Double ) : LiveData<OpenWeatherResult>

    /**
     * by zip code
     * api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}
     */
    @GET("weather?=zip{zipCode},{contryCode}")
    fun getWeatherByZipCode( @Path("zipCode") zipCode: String, @Path("contryCode") contryCode: String ) : LiveData<OpenWeatherResult>




}