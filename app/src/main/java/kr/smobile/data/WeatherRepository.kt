package kr.smobile.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.smobile.AppExecutors
import kr.smobile.core.api.ApiResponse
import kr.smobile.core.api.WeatherService
import kr.smobile.core.db.WeatherDao
import kr.smobile.core.testing.OpenForTesting
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 날씨 정보 Repository
 */
@OpenForTesting
@Singleton
class WeatherRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
    ) {

    private fun isNeedRefresh(timems : Long) : Boolean {
        val infoDate = Calendar.getInstance()
        infoDate.timeInMillis = timems

        //3시간이 지난 데이터의 경우 다시 가져온다.
        val boundary = Calendar.getInstance()
        boundary.add(Calendar.HOUR_OF_DAY,-3)

        return infoDate.before(boundary)
    }

    // 최신 날씨 정보 가져오기
    fun loadLatestWeatherByCityName(cityName: String) : LiveData<Resource<OpenWeatherResult>> {

        return object : NetworkBoundResource<OpenWeatherResult,OpenWeatherResult>(appExecutors) {
            override fun saveCallResult(item: OpenWeatherResult) {
                item.saveTime = Date().time
                weatherDao.insert(item)
            }

            override fun shouldFetch(data: OpenWeatherResult?): Boolean {
                return false //data == null  || isNeedRefresh(data.saveTime ?: 0)
            }

            override fun loadFromDb(): LiveData<OpenWeatherResult>
                    = weatherDao.getLatestWeatherByCityName(cityName)


            override fun createCall(): LiveData<ApiResponse<OpenWeatherResult>>
                    = weatherService.getWeatherByCityName(cityName)
        }.asLiveData()
    }

    fun loadLatesWeatherByCityId( cityId: Int) : LiveData<Resource<OpenWeatherResult>> {
        return object : NetworkBoundResource<OpenWeatherResult,OpenWeatherResult>(appExecutors) {
            override fun saveCallResult(item: OpenWeatherResult)
                    = weatherDao.insert(item)

            override fun shouldFetch(data: OpenWeatherResult?): Boolean {
                return false //data == null || isNeedRefresh(data.datetime)
            }

            override fun loadFromDb(): LiveData<OpenWeatherResult>
                    = weatherDao.getLatestWeatherByCityId(cityId)


            override fun createCall(): LiveData<ApiResponse<OpenWeatherResult>>
                    = weatherService.getWeatherByCityId(cityId)

        }.asLiveData()
    }

    fun loadForeCastWeather( cityId : Int) : LiveData<Resource<ForeCastResult>> {
        return object : NetworkBoundResource<ForeCastResult,ForeCastResult>(appExecutors) {
            override fun saveCallResult(item: ForeCastResult) {
                item.createAt = Date().time
                weatherDao.insert(item)
            }

            override fun shouldFetch(data: ForeCastResult?): Boolean {
                return false //data == null || isNeedRefresh(data.createAt) // 데이터가 없다면 서버로부터 요청
            }

            override fun loadFromDb(): LiveData<ForeCastResult>
                = weatherDao.getForecastByCityId(cityId)

            override fun createCall(): LiveData<ApiResponse<ForeCastResult>>
                = weatherService.getForecastByCityId(cityId)
        }.asLiveData()
    }

    // demo test
    // only cloud function
    /*
    fun checkPrivateKey( key: String) : LiveData< Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean,Boolean>(appExecutors) {
            override fun saveCallResult(item: Boolean) {
                //ignore
            }
            override fun shouldFetch(data: Boolean?): Boolean = true
            override fun loadFromDb(): LiveData<Boolean> {
                return MutableLiveData()
            }
            override fun createCall(): LiveData<ApiResponse<Boolean>> {
                return //
            }
        }.asLiveData()
    }
    */

}