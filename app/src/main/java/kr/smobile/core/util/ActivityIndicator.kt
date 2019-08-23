package kr.smobile.core.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kr.smobile.vo.Resource

class ActivityIndicator {

    private val loadingLiveData = MediatorLiveData<Boolean>()
    private val loadingConcernedLiveData : MutableList<LiveData<out Resource<*>>> = mutableListOf()

    fun regLoadingConcernLiveData(listItems: List<LiveData<out Resource<Any>>>) {
        loadingConcernedLiveData.clear()
        loadingConcernedLiveData.addAll(listItems)
        init()
    }

    fun regLoadingConcernLiveData(vararg items: LiveData<out Resource<*>>) {
        loadingConcernedLiveData.clear()
        loadingConcernedLiveData.addAll(items.toList())
        init()
    }

    private fun init() {
        loadingConcernedLiveData.forEach {
            loadingLiveData.addSource(it) {
                loadingLiveData.value = combineLatesData()
            }
        }

    }

    private fun combineLatesData() : Boolean {

        val isLoading = loadingConcernedLiveData.any { it.value is Resource.Loading }
        return isLoading
    }

    fun asLiveData() : LiveData<Boolean> = loadingLiveData


}