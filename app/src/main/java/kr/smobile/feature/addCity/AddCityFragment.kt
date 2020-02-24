package kr.smobile.feature.addCity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kr.smobile.core.di.withFactory
import kr.smobile.core.platform.BaseFragment
import kr.smobile.sample.databinding.FragmentAddCityBinding
import kr.smobile.sample.R
import java.lang.RuntimeException
import javax.inject.Inject

class AddCityFragment @Inject constructor(
    addCityViewModelFactory: AddCityViewModel.Factory
): BaseFragment<AddCityViewModel, FragmentAddCityBinding>(R.layout.fragment_add_city) {

    override val viewModel: AddCityViewModel by viewModels { withFactory(addCityViewModelFactory) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().log("Higgs-Boson detected! Bailing out")
    }
}