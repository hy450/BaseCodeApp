package kr.smobile.core.extension

import io.reactivex.Maybe

fun <T> Maybe<T>.subscribeWithEmptyHandler(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit, onEmpty: () -> Unit) {
    this.toSingle()
        .subscribe(
            { onSuccess(it) },
            { if (it is NoSuchElementException) onEmpty() else onError(it) }
        )
}