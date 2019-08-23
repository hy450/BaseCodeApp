package kr.smobile

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kr.smobile.core.di.AppInjector
import kr.smobile.core.logging.FileLoggingTree
import timber.log.Timber
import javax.inject.Inject

class BaseApplication : Application() , HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree(), FileLoggingTree())
        } else {
            Timber.plant(FileLoggingTree())
        }

        AppInjector.init(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector


}