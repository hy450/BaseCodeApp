package kr.smobile.feature.home

import androidx.lifecycle.*
import kr.smobile.core.platform.BaseViewModel
import kr.smobile.core.testing.OpenForTesting
import kr.smobile.core.util.AbsentLiveData
import kr.smobile.core.util.ActivityIndicator
import kr.smobile.data.WeatherRepository
import kr.smobile.vo.*
import javax.inject.Inject

/**
 * 자기가 즐겨찾기한 현재의 날씨 정보를 보여준다.
 */
@OpenForTesting
class HomeViewModel constructor(
    private val handle: SavedStateHandle,
    private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    //var loadingLiveData = MediatorLiveData<Boolean>()
    private val _currFavorCityId = MutableLiveData<Int>()
    val currFavorCityId: LiveData<Int>
        get() = _currFavorCityId

    // 현재 날씨 정보
    val currMainWeatherInfo: LiveData<MainInfo?> by lazy {
        favorCityWeatherRepo.map {
            when(it) {
                is Resource.Success -> it.data?.mainWeatherInfo
                is Resource.Loading -> it.data?.mainWeatherInfo
                else -> null
            }
        }
    }
    //현재 날씨 로딩 에러 여
    val currMainWeatherLoadError: LiveData<Boolean> by lazy {
        favorCityWeatherRepo.map {
            when(it) {
                is Resource.Success -> false
                is Resource.Loading -> false
                else -> true
            }
        }
    }
    // 현재 날씨의 icon
    val currWeatherIcon: LiveData<String?> by lazy {
        favorCityWeatherRepo.map {
            when(it) {
                is Resource.Success -> it.data?.weatherItems?.firstOrNull()?.icon
                is Resource.Loading -> it.data?.weatherItems?.firstOrNull()?.icon
                else -> null
            }
        }
    }


    // 시간대별 날씨 정보
    val forecastWeather: LiveData<List<HourlyWeatherUiModel>> by lazy {
        favorHourlyWeatherRepo.map {
            when(it) {
                is Resource.Success -> it.data?.list?.map { HourlyWeatherUiModel.convert(it) } ?: emptyList()
                is Resource.Loading -> it.data?.list?.map { HourlyWeatherUiModel.convert(it) } ?: emptyList()
                is Resource.Error -> emptyList()
            }
        }
    }


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


    class Factory @Inject constructor(
        private val weatherRepository: WeatherRepository
    ) : BaseViewModel.Factory<HomeViewModel>() {
        override fun create(handle: SavedStateHandle): HomeViewModel {
            return HomeViewModel(
                handle,
                weatherRepository
            )
        }
    }

}