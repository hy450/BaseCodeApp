package kr.smobile.vo

import kr.smobile.core.error.Failure

sealed class Resource<out T> {
    //network 로 데이터를 가져올때까지 db의 최신 값을 설정하기 위해서 data property
    class Loading<out T>(val data: T?) : Resource<T>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error<out T>( val failure: Failure) : Resource<T>()
}