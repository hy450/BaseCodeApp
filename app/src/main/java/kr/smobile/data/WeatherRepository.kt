package kr.smobile.data

import androidx.lifecycle.LiveData
import kr.smobile.AppExecutors
import kr.smobile.core.api.ApiResponse
import kr.smobile.core.api.WeatherService
import kr.smobile.core.db.WeatherDao
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource
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

    // 최신 날씨 정보 가져오기
    fun loadLatestWeatherByCityName(cityName: String) : LiveData<Resource<OpenWeatherResult>> {

        return object : NetworkBoundResource<OpenWeatherResult,OpenWeatherResult>(appExecutors) {
            override fun saveCallResult(item: OpenWeatherResult)
                    = weatherDao.insert(item)

            override fun shouldFetch(data: OpenWeatherResult?): Boolean {
                //TODO 몇시간이 지난 데이터의 경우 다시 가져온다.
                return data == null
            }

            override fun loadFromDb(): LiveData<OpenWeatherResult>
                    = weatherDao.getLatestWeatherByCityName(cityName)


            override fun createCall(): LiveData<ApiResponse<OpenWeatherResult>>
                    = weatherService.getWeatherByCityName(cityName)
        }.asLiveData()
    }

}