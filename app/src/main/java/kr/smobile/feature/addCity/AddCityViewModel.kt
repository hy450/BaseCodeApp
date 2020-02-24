package kr.smobile.feature.addCity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import kr.smobile.core.platform.BaseViewModel
import kr.smobile.core.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class AddCityViewModel @Inject constructor(
    private val handle: SavedStateHandle
)
    : BaseViewModel() {


    override val loadingEvent: LiveData<Boolean> by lazy {
        MediatorLiveData<Boolean>()
    }

    class Factory @Inject constructor() : BaseViewModel.Factory<AddCityViewModel>() {
        override fun create(handle: SavedStateHandle): AddCityViewModel {
            return AddCityViewModel(
                handle
            )
        }
    }

}