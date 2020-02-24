package kr.smobile.feature.addCity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.smobile.R
import kr.smobile.core.di.Injectable
import kr.smobile.core.di.withFactory
import kr.smobile.core.extension.viewModel
import kr.smobile.core.platform.BaseFragment
import javax.inject.Inject

class AddCityFragment @Inject constructor(
    addCityViewModelFactory: AddCityViewModel.Factory
): BaseFragment<AddCityViewModel>() {

    override val viewModel: AddCityViewModel by viewModels { withFactory(addCityViewModelFactory) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_city, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}