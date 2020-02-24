package kr.smobile.feature.home


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kr.smobile.core.di.withFactory
import kr.smobile.core.extension.debug
import kr.smobile.core.platform.BaseFragment
import kr.smobile.sample.databinding.FragmentHomeBinding
import kr.smobile.sample.R
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment @Inject constructor(
    homeViewModelFactory: HomeViewModel.Factory
): BaseFragment<HomeViewModel,FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels { withFactory(homeViewModelFactory) }


    private val hourlyListViewAdapter by lazy {
        HourlyWeatherAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debug("onCreate")
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.forecastWeather.observe(viewLifecycleOwner) {
            hourlyListViewAdapter.hourlyWeatherInfos = it
        }

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

}
