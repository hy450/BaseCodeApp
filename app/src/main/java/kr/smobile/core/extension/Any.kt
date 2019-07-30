package kr.smobile.core.extension

import android.util.Log

inline fun <reified T> T.debug(message: String) = Log.d(T::class.java.simpleName, message)
inline fun <reified T> T.error(message: String) = Log.e(T::class.java.simpleName, message)
inline fun <reified T> T.warn(message: String) = Log.w(T::class.java.simpleName, message)