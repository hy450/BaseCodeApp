package kr.smobile.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import kr.smobile.core.api.WeatherService
import kr.smobile.core.db.WeatherDao
import kr.smobile.core.util.InstantAppExecutors
import kr.smobile.core.util.TestUtil
import kr.smobile.data.WeatherRepository
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit


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

        val weatherResult = TestUtil.createWeather()
        val data = Maybe.just( weatherResult)

        `when`(weatherDao.getLatestWeatherByCityName("London")).thenReturn(data)

        val weatherResult1 = TestUtil.createWeather1()
        val call = Flowable.just(weatherResult1)
        `when`(weatherService.getWeatherByCityName("London")).thenReturn(call)

        val testSubscriber = TestSubscriber.create<Resource<OpenWeatherResult>>()
        repo.loadLatestWeatherByCityName("London")
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(testSubscriber)

        Thread.sleep(3000)

        testSubscriber.assertValueAt(0) {
            it is Resource.Loading &&
                    it.data?.cityId == weatherResult.cityId &&
                    it.data?.cityName == weatherResult.cityName
        }

        testSubscriber.assertValueAt(1) {
            it is Resource.Success &&
                    it.data?.cityId == weatherResult1.cityId &&
                    it.data?.cityName == weatherResult1.cityName
        }
        testSubscriber.assertComplete()
        testSubscriber.dispose()
    }

    @Test
    fun loadWeatherFromDbAndRefresh() {
//        val data = MutableLiveData<OpenWeatherResult>()
//        val weatherResult = TestUtil.createWeather()
//        data.value = weatherResult
//
//        `when`(weatherDao.getLatestWeatherByCityName("London")).thenReturn(data)
//
//        val newWeatherResult = TestUtil.createWeather()
//        val call = MutableLiveData<ApiResponse<OpenWeatherResult>>().apply {
//            value = ApiResponse.create( Response.success(newWeatherResult))
//        }
//        `when`(weatherService.getWeatherByCityName("London")).thenReturn(call)
//
//        val observer = mock<Observer<Resource<OpenWeatherResult>>>()
//        repo.loadLatestWeatherByCityName("London").observeForever(observer)
//
//        verify(weatherService).getWeatherByCityName("London")
//        verify(observer).onChanged(Resource.Success(weatherResult))
//        verify(observer).onChanged(Resource.Success(newWeatherResult))

    }

}