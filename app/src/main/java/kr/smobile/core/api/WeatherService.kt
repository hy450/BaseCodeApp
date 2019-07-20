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

    override fun getWeatherByCityName(cityName: String): LiveData<OpenWeatherResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeatherByCityId(cityId: String): LiveData<OpenWeatherResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeatherByCityIds(cityId: String, units: String): LiveData<OpenWeatherResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeatherByCoordinate(lat: Double, lon: Double): LiveData<OpenWeatherResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeatherByZipCode(zipCode: String, contryCode: String): LiveData<OpenWeatherResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}