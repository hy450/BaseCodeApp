package kr.smobile

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kr.smobile.core.di.DaggerApplicationComponent
import kr.smobile.core.logging.FileLoggingTree
import timber.log.Timber

class BaseApplication : DaggerApplication() {

    private val component: AndroidInjector<BaseApplication> by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree(), FileLoggingTree())
        } else {
            Timber.plant(FileLoggingTree())
        }
    }

}