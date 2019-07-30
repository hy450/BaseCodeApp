package kr.smobile.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.smobile.core.util.AbsentLiveData
import kr.smobile.data.WeatherRepository
import kr.smobile.feature.BaseViewModel
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenForeCastWeather
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource
import timber.log.Timber
import javax.inject.Inject

/**
 * 자기가 즐겨찾기한 현재의 날씨 정보를 보여준다.
 */
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    private val _currFavorCityId = MutableLiveData<Int>()
    val currFavorCityId: LiveData<Int>
        get() = _currFavorCityId

    val favorCityWeatherRepo : LiveData<Resource<OpenWeatherResult>> = Transformations.switchMap(_currFavorCityId) { cityId ->
        if(cityId == null) {
            AbsentLiveData.create()
        } else {
            weatherRepository.loadLatesWeatherByCityId(cityId)
        }
    }

    val favorHourlyWeatherRepo : LiveData<Resource<ForeCastResult>> = Transformations.switchMap(_currFavorCityId) { cityId ->
        if(cityId == null) {
            AbsentLiveData.create()
        } else {
            weatherRepository.loadForeCastWeather(cityId)
        }
    }

    /**
     * 현재 즐겨찾는 도시를 선택
     */
    fun setCurrFavoriteCity( cityId : Int ) {
        _currFavorCityId.value = cityId
    }


}