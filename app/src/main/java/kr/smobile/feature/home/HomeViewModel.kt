package kr.smobile.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kr.smobile.core.platform.BaseViewModel
import kr.smobile.core.testing.OpenForTesting
import kr.smobile.core.util.AbsentLiveData
import kr.smobile.core.util.ActivityIndicator
import kr.smobile.data.WeatherRepository
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource
import javax.inject.Inject

/**
 * 자기가 즐겨찾기한 현재의 날씨 정보를 보여준다.
 */
@OpenForTesting
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    //var loadingLiveData = MediatorLiveData<Boolean>()

    private val _currFavorCityId = MutableLiveData<Int>()
    val currFavorCityId: LiveData<Int>
        get() = _currFavorCityId

    val favorCityWeatherRepo : LiveData<Resource<OpenWeatherResult>> = Transformations.switchMap(_currFavorCityId) { cityId ->
        if(cityId == null) {
            AbsentLiveData.create()
        } else {
            LiveDataReactiveStreams.fromPublisher(weatherRepository.loadLatesWeatherByCityId(cityId))
        }
    }

    val favorHourlyWeatherRepo : LiveData<Resource<ForeCastResult>> = Transformations.switchMap(_currFavorCityId) { cityId ->
        if(cityId == null) {
            AbsentLiveData.create()
        } else {
            LiveDataReactiveStreams.fromPublisher(weatherRepository.loadForeCastWeather(cityId))
        }
    }


    override val loadingEvent by lazy {
        ActivityIndicator().apply {
            regLoadingConcernLiveData(favorCityWeatherRepo,favorHourlyWeatherRepo)
        }.asLiveData()
    }

    /**
     * 현재 즐겨찾는 도시를 선택
     */
    fun setCurrFavoriteCity( cityId : Int ) {
        _currFavorCityId.value = cityId
    }


}