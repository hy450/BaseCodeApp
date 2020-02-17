package kr.smobile.data

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kr.smobile.core.error.Failure
import kr.smobile.core.extension.debug
import kr.smobile.core.extension.subscribeWithEmptyHandler
import kr.smobile.vo.Resource
import timber.log.Timber

abstract class RxNetworkBoundResource<ResultType, RequestType> {
    private val result: Flowable<Resource<ResultType>>


    init {
        println("RxNetworkBoundResource")
        val source: Flowable<Resource<ResultType>>

        val loadFromDbSingle = Single.create<ResultType> { emitter ->
            loadFromDb().subscribeWithEmptyHandler(
                { emitter.onSuccess(it) },
                { emitter.onError(it) },
                { emitter.onError(NoSuchElementException()) }
            )
        }

        @Suppress("LeakingThis")
        source = shouldFetch(loadFromDbSingle)
            .toFlowable()
            .flatMap {
                if(it){
                    createCall()
                        .map {response ->
                            val resourceReturnType : Resource<ResultType>
                            resourceReturnType = Resource.Success( map(response))
                            resourceReturnType
                        }
                        .onErrorReturn { throwable ->
                            Resource.Error( Failure.ServerResponseError )
                        }
                }
                else {
                    loadFromDb()
                        .map { Resource.Success(it) as Resource<ResultType> }
                        .toFlowable()
                }
            }
            .onErrorReturn {
                Resource.Error(Failure.ServerResponseError)
            }

        result = Flowable.concat(
            loadFromDb()
                .map { Resource.Loading(it) }
                .toFlowable(),
            source)
            .distinct()
            .subscribeOn(Schedulers.io())
    }

    fun asFlowable(): Flowable<Resource<ResultType>> {
        return result
    }

    protected fun onFetchFailed(t: Throwable) {
        Timber.e(t)
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun map(item: RequestType): ResultType {
        return item as (ResultType)
    }

    protected abstract fun saveCallResult(item: RequestType)
    protected abstract fun shouldFetch(item: Single<ResultType>): Single<Boolean>
    protected abstract fun loadFromDb(): Maybe<ResultType>
    protected abstract fun createCall(): Flowable<RequestType>
    protected open fun onFetchFailed(failure: Failure) {}
}