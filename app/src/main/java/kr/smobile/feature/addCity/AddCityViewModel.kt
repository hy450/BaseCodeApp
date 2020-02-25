package kr.smobile.feature.addCity

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kr.smobile.core.platform.BaseViewModel
import kr.smobile.core.testing.OpenForTesting
import kr.smobile.core.util.SingleLiveEvent
import javax.inject.Inject


@OpenForTesting
class AddCityViewModel @Inject constructor(
    private val handle: SavedStateHandle
)
    : BaseViewModel() {

    val navigatorLiveData: SingleLiveEvent<NavigatorEvent> = SingleLiveEvent()


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

    fun googleSigninBtnClick(context: Context) {
        navigatorLiveData.postValue(NavigatorEvent.RequestGoogleSignIn)
    }

    enum class NavigatorEvent {
        RequestGoogleSignIn

    }
}