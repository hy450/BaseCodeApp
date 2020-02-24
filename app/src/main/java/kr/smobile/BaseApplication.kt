package kr.smobile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kr.smobile.core.di.DaggerApplicationComponent
import kr.smobile.core.logging.FileLoggingTree
import kr.smobile.sample.BuildConfig
import kr.smobile.sample.R
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
            Timber.plant(Timber.DebugTree(), FileLoggingTree(applicationContext))
        } else {
            Timber.plant(FileLoggingTree(applicationContext))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW)
            )
        }
    }

}