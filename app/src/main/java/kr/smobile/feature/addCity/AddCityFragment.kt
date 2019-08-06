package kr.smobile.feature.addCity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.smobile.R
import kr.smobile.core.di.Injectable
import kr.smobile.core.extension.viewModel
import kr.smobile.feature.BaseFragment

class AddCityFragment : BaseFragment<AddCityViewModel>(), Injectable {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_city, container, false)
    }

    override fun createViewModel(): AddCityViewModel {
        return viewModel(viewModelFactory) {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}