package kr.smobile.feature.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_home.*
import kr.smobile.R
import kr.smobile.core.di.Injectable
import kr.smobile.core.extension.debug
import kr.smobile.core.extension.observe
import kr.smobile.core.extension.viewModel
import kr.smobile.feature.BaseFragment
import kr.smobile.vo.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment<HomeViewModel>(), Injectable {

    //val viewmodel by viewModels<HomeViewModel> { SavedStateViewModelFactory(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun createViewModel(): HomeViewModel {
        return viewModel(viewModelFactory) {
            observe(favorCityWeatherRepo, ::updateCurrWeatherInfo)
            observe(favorHourlyWeatherRepo, ::updateForeCastWeather)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doActionSelectFavoriteCity(1835848) // seoul
    }

    /**
     * 홈화면의 날씨를 보여줄 도시 선택
     */
    private fun doActionSelectFavoriteCity(cityId: Int) {
        viewModel.setCurrFavoriteCity(cityId)
    }

    private fun updateCurrWeatherInfo(weatherInfo : Resource<OpenWeatherResult>?) {
        debug("updateCurrWeatherInfo")

        when(weatherInfo) {
            is Resource.Success ->
                displayCurrWeather(weatherInfo.data?.mainWeatherInfo)
            is Resource.Loading ->
                displayCurrWeather(weatherInfo.data?.mainWeatherInfo)
            is Resource.Error -> {
                homeTempTxt.text = "error"
                minMaxTempTxt.isVisible = false
            }
        }
    }

    private fun displayCurrWeather(weatherMain: MainInfo?) {
        homeTempTxt.text = getString(R.string.home_celius_temperature,weatherMain?.temperature?.toInt()?.toString() ?: "-")
        minMaxTempTxt.text = getString(R.string.home_minmax_temperature,weatherMain?.temp_min?.toInt() ?: 0, weatherMain?.temp_max?.toInt() ?: 0)
    }

    private fun updateForeCastWeather( foreCastWeather: Resource<ForeCastResult>? ) {
        debug("updateForeCastWeather")
    }
}
