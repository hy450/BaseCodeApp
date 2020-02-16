package kr.smobile.core.error

sealed class Failure {

    object UnknownError: Failure()

    object ServerResponseError: Failure()


}