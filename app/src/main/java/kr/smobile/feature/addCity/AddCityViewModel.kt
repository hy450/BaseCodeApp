package kr.smobile.feature.addCity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kr.smobile.core.platform.BaseViewModel
import kr.smobile.core.testing.OpenForTesting
import kr.smobile.feature.MainViewModel
import javax.inject.Inject

@OpenForTesting
class AddCityViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle
)
    : BaseViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle) : MainViewModel
    }

    override val loadingEvent: LiveData<Boolean> by lazy {
        MediatorLiveData<Boolean>()
    }

}