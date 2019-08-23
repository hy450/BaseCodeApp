package kr.smobile.feature.addCity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kr.smobile.core.platform.BaseViewModel
import kr.smobile.core.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class AddCityViewModel @Inject constructor()
    : BaseViewModel() {

    override val loadingEvent: LiveData<Boolean> by lazy {
        MediatorLiveData<Boolean>()
    }

}