package kr.smobile.data

import androidx.lifecycle.LiveData
import kr.smobile.AppExecutors
import kr.smobile.core.api.ApiResponse
import kr.smobile.core.api.WeatherService
import kr.smobile.core.db.WeatherDao
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 날씨 정보 Repository
 */
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
            override fun saveCallResult(item: OpenWeatherResult)
                    = weatherDao.insert(item)

            override fun shouldFetch(data: OpenWeatherResult?): Boolean {
                return data == null  || isNeedRefresh(data.datetime)
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
                return data == null || isNeedRefresh(data.datetime)
            }

            override fun loadFromDb(): LiveData<OpenWeatherResult>
                    = weatherDao.getLatestWeatherByCityId(cityId)


            override fun createCall(): LiveData<ApiResponse<OpenWeatherResult>>
                    = weatherService.getWeatherByCityId(cityId)

        }.asLiveData()
    }

}