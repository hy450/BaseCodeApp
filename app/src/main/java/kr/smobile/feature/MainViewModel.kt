package kr.smobile.feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kr.smobile.feature.home.HomeViewModel
import javax.inject.Inject

class MainViewModel @AssistedInject constructor(
    @Assisted private val handle: SavedStateHandle
): ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle) : MainViewModel
    }
}