package kr.smobile.feature.home


import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kr.smobile.R
import kr.smobile.core.di.withFactory
import kr.smobile.core.extension.debug
import kr.smobile.core.extension.observe
import kr.smobile.core.extension.viewModel
import kr.smobile.core.platform.BaseFragment
import kr.smobile.databinding.FragmentHomeBinding
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.MainInfo
import kr.smobile.vo.OpenWeatherResult
import kr.smobile.vo.Resource
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
