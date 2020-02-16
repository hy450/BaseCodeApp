package kr.smobile.data

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import kr.smobile.AppExecutors
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
    fun loadLatestWeatherByCityName(cityName: String) : Flowable<Resource<OpenWeatherResult>> {

        return object : RxNetworkBoundResource<OpenWeatherResult,OpenWeatherResult>() {
            override fun saveCallResult(item: OpenWeatherResult) {
                item.saveTime = Date().time
                weatherDao.insert(item)
            }

            override fun shouldFetch(item: Single<OpenWeatherResult>): Single<Boolean> {
                return Single.just(true)
            }

            override fun loadFromDb(): Maybe<OpenWeatherResult>
                    = weatherDao.getLatestWeatherByCityName(cityName)


            override fun createCall(): Flowable<OpenWeatherResult>
                    = weatherService.getWeatherByCityName(cityName)
        }.asFlowable()
    }

    fun loadLatesWeatherByCityId( cityId: Int) : Flowable<Resource<OpenWeatherResult>> {
        return object : RxNetworkBoundResource<OpenWeatherResult,OpenWeatherResult>() {
            override fun saveCallResult(item: OpenWeatherResult)
                    = weatherDao.insert(item)

            override fun shouldFetch(item: Single<OpenWeatherResult>): Single<Boolean> {
                return Single.just(true)
            }

            override fun loadFromDb(): Maybe<OpenWeatherResult>
                    = weatherDao.getLatestWeatherByCityId(cityId)


            override fun createCall(): Flowable<OpenWeatherResult>
                    = weatherService.getWeatherByCityId(cityId)

        }.asFlowable()
    }

    fun loadForeCastWeather( cityId : Int) : Flowable<Resource<ForeCastResult>> {
        return object : RxNetworkBoundResource<ForeCastResult,ForeCastResult>() {
            override fun saveCallResult(item: ForeCastResult) {
                item.createAt = Date().time
                weatherDao.insert(item)
            }

            override fun shouldFetch(item: Single<ForeCastResult>): Single<Boolean> {
                return Single.just(true)
            }

            override fun loadFromDb(): Maybe<ForeCastResult>
                = weatherDao.getForecastByCityId(cityId)

            override fun createCall(): Flowable<ForeCastResult>
                = weatherService.getForecastByCityId(cityId)
        }.asFlowable()
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