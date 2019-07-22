package kr.smobile.data

import kr.smobile.AppExecutors
import kr.smobile.core.api.WeatherService
import kr.smobile.core.db.WeatherDao
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

    //

}