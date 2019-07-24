package kr.smobile.core.util

import kr.smobile.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant,instant,instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}