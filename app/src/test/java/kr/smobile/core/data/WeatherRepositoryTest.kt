package kr.smobile.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kr.smobile.core.api.ApiResponse
import kr.smobile.core.api.WeatherService
import kr.smobile.core.db.WeatherDao
import kr.smobile.core.util.InstantAppExecutors
import kr.smobile.core.util.TestUtil
import kr.smobile.core.util.mock
import kr.smobile.data.WeatherRepository
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import retrofit2.Response


@RunWith(JUnit4::class)
class WeatherRepositoryTest {

    private val weatherDao = mock(WeatherDao::class.java)
    private val weatherService = mock(WeatherService::class.java)

    private val repo = WeatherRepository(InstantAppExecutors(),weatherService,weatherDao)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Test
    fun loadWeather() {
        repo.loadLatestWeatherByCityName("London")
        verify(weatherDao).getLatestWeatherByCityName("London")
    }

    @Test
    fun loadWeatherFromNetwork() {
        val data = MutableLiveData<OpenWeatherResult>()
        `when`(weatherDao.getLatestWeatherByCityName("London")).thenReturn(data)

        val weatherResult = TestUtil.createWeather()
        val call = MutableLiveData<ApiResponse<OpenWeatherResult>>().apply {
            value = ApiResponse.create( Response.success(weatherResult))
        }
        `when`(weatherService.getWeatherByCityName("London")).thenReturn(call)

        val observer = mock<Observer<Resource<OpenWeatherResult>>>()
        repo.loadLatestWeatherByCityName("London").observeForever(observer)

        verify(weatherService, never()).getWeatherByCityName("London")


        val updatedDbData = MutableLiveData<OpenWeatherResult>()
        `when`(weatherDao.getLatestWeatherByCityName("London")).thenReturn(updatedDbData)
        data.value = null
        verify(weatherService).getWeatherByCityName("London")
    }

    @Test
    fun loadWeatherFromDbAndRefresh() {
        val data = MutableLiveData<OpenWeatherResult>()
        val weatherResult = TestUtil.createWeather()
        data.value = weatherResult

        `when`(weatherDao.getLatestWeatherByCityName("London")).thenReturn(data)

        val newWeatherResult = TestUtil.createWeather()
        val call = MutableLiveData<ApiResponse<OpenWeatherResult>>().apply {
            value = ApiResponse.create( Response.success(newWeatherResult))
        }
        `when`(weatherService.getWeatherByCityName("London")).thenReturn(call)

        val observer = mock<Observer<Resource<OpenWeatherResult>>>()
        repo.loadLatestWeatherByCityName("London").observeForever(observer)

        verify(weatherService).getWeatherByCityName("London")
        verify(observer).onChanged(Resource.Success(weatherResult))
        verify(observer).onChanged(Resource.Success(newWeatherResult))

    }

}