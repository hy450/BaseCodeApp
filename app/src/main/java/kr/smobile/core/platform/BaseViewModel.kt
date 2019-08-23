package kr.smobile.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kr.smobile.core.util.ActivityIndicator
import kr.smobile.core.util.SingleLiveEvent


abstract class BaseViewModel: ViewModel() {

    protected val compositeDisposables = CompositeDisposable()

    private val _showAlertEvent = SingleLiveEvent<VmEvent<AlertEvent>>()
    val showAlertEvent: SingleLiveEvent<VmEvent<AlertEvent>>
        get() = _showAlertEvent


    val activityIndicator = ActivityIndicator()
    abstract val loadingEvent : LiveData<Boolean>


    override fun onCleared() {
        super.onCleared()
        compositeDisposables.clear()

    }

}