package kr.smobile.feature.home


import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kr.smobile.R
import kr.smobile.core.di.Injectable
import kr.smobile.core.extension.debug
import kr.smobile.core.extension.observe
import kr.smobile.core.extension.viewModel
import kr.smobile.core.platform.BaseFragment
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment<HomeViewModel>(), Injectable {

    //val viewmodel by viewModels<HomeViewModel> { SavedStateViewModelFactory(this) }

    private val hourlyListViewAdapter by lazy {
        HourlyWeatherAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debug("HomeFragment onCreate")
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        debug("HomeFragment onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun createViewModel(): HomeViewModel {
        return viewModel(viewModelFactory) {
            viewLifecycleOwner.observe(favorCityWeatherRepo, ::updateCurrWeatherInfo)
            viewLifecycleOwner.observe(favorHourlyWeatherRepo, ::updateForeCastWeather)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadingEvent.observe(viewLifecycleOwner,showLoadingObserver)

        hourlyListView.adapter = hourlyListViewAdapter

        doActionSelectFavoriteCity(1835848) // seoul
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_add_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_city_change -> {
                findNavController().navigate( HomeFragmentDirections.actionHomeFragmentToAddCityFragment() )
                true
            }
            else -> false


        }
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
            is Resource.Success -> {
                debug("updateCurrWeatherInfo success")
                displayCurrWeather(weatherInfo.data)
            }
            is Resource.Loading -> {
                // weatherInfo.data 가 null 이면 loading 아이콘???
                weatherInfo.data?.let {
                    debug("updateCurrWeatherInfo Loading")
                    displayCurrWeather(it)
                }
            }
            is Resource.Error -> {
                debug("updateCurrWeatherInfo error")
                homeTempTxt.text = "error"
                minMaxTempTxt.isVisible = false
            }
        }
    }

    private fun displayCurrWeather(result: OpenWeatherResult?) {
        val weatherMain = result?.mainWeatherInfo
        homeTempTxt.text = getString(R.string.home_celius_temperature,weatherMain?.temperature?.toInt()?.toString() ?: "-")
        minMaxTempTxt.text = getString(R.string.home_minmax_temperature,weatherMain?.temp_min?.toInt() ?: 0, weatherMain?.temp_max?.toInt() ?: 0)

        result?.weatherItems?.firstOrNull()?.let {
            Glide.with(this)
                .load("http://openweathermap.org/img/wn/${it.icon}@2x.png")
                .into(weatherImg)
        }

    }

    private fun updateForeCastWeather( foreCastWeather: Resource<ForeCastResult>? ) {
        debug("updateForeCastWeather")

        when(foreCastWeather) {
            is Resource.Success ->
                hourlyListViewAdapter.hourlyWeatherInfos = foreCastWeather.data?.list ?: emptyList()

            is Resource.Loading -> {
                hourlyListViewAdapter.hourlyWeatherInfos = foreCastWeather.data?.list ?: emptyList()
            }

            is Resource.Error -> {

            }

        }


    }
}
