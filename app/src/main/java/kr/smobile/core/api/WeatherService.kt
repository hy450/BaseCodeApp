package kr.smobile.core.api

import androidx.lifecycle.LiveData
import kr.smobile.vo.OpenWeatherResult
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class WeatherService @Inject constructor( @Named( "openweather") retrofit: Retrofit ) : WeatherRestApiService {

    private val retrofitApi by lazy {
        retrofit.create( WeatherRestApiService::class.java)
    }

    override fun getWeatherByCityName(cityName: String): LiveData<OpenWeatherResult>
            = retrofitApi.getWeatherByCityName(cityName = cityName)

    override fun getWeatherByCityId(cityId: String): LiveData<OpenWeatherResult>
            = retrofitApi.getWeatherByCityId(cityId = cityId)


    override fun getWeatherByCityIds(cityId: String, units: String): LiveData<OpenWeatherResult>
            = retrofitApi.getWeatherByCityIds(cityId, units)

    override fun getWeatherByCoordinate(lat: Double, lon: Double): LiveData<OpenWeatherResult>
            = retrofitApi.getWeatherByCoordinate(lat = lat, lon = lon)

    override fun getWeatherByZipCode(zipCode: String, contryCode: String): LiveData<OpenWeatherResult>
            = retrofitApi.getWeatherByZipCode(zipCode,contryCode)
}