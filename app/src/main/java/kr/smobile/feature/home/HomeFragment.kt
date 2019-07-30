package kr.smobile.feature.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kr.smobile.R
import kr.smobile.core.di.Injectable
import kr.smobile.core.extension.debug
import kr.smobile.core.extension.observe
import kr.smobile.core.extension.viewModel
import kr.smobile.feature.BaseFragment
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenForeCastWeather
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource


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

        viewModel.setCurrFavoriteCity(1835848) // seoul


    }

    private fun updateCurrWeatherInfo(weatherInfo : Resource<OpenWeatherResult>?) {
        debug("updateCurrWeatherInfo")

    }

    private fun updateForeCastWeather( foreCastWeather: Resource<ForeCastResult>? ) {
        debug("updateForeCastWeather")
    }
}
