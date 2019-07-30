package kr.smobile.core.api

import androidx.lifecycle.LiveData
import kr.smobile.core.testing.OpenForTesting
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenWeatherResult
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@OpenForTesting
@Singleton
class WeatherService @Inject constructor( @Named( "openweather") retrofit: Retrofit ) : WeatherRestApiService {

    private val retrofitApi by lazy {
        retrofit.create( WeatherRestApiService::class.java)
    }

    override fun getWeatherByCityName(cityName: String): LiveData<ApiResponse<OpenWeatherResult>>
            = retrofitApi.getWeatherByCityName(cityName = cityName)

    override fun getWeatherByCityId(cityId: Int): LiveData<ApiResponse<OpenWeatherResult>>
            = retrofitApi.getWeatherByCityId(cityId = cityId)


    override fun getWeatherByCityIds(cityIds: String, units: String): LiveData<ApiResponse<OpenWeatherResult>>
            = retrofitApi.getWeatherByCityIds(cityIds, units)

    override fun getWeatherByCoordinate(lat: Double, lon: Double): LiveData<ApiResponse<OpenWeatherResult>>
            = retrofitApi.getWeatherByCoordinate(lat = lat, lon = lon)

    override fun getWeatherByZipCode(zipCode: String, contryCode: String): LiveData<ApiResponse<OpenWeatherResult>>
            = retrofitApi.getWeatherByZipCode(zipCode,contryCode)

    override fun getForecastByCityName(cityName: String): LiveData<ApiResponse<ForeCastResult>>
            = retrofitApi.getForecastByCityName(cityName)

    override fun getForecastByCityId(cityId: Int): LiveData<ApiResponse<ForeCastResult>>
            = retrofitApi.getForecastByCityId(cityId)
}