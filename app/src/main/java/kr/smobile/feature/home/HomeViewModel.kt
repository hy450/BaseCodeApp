package kr.smobile.feature.home

import kr.smobile.data.WeatherRepository
import kr.smobile.feature.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    fun getFavoriteWeatherInfos() {

        Timber.d("HomeViewModel getFavoriteWeatherInfos()")

    }

}