package kr.smobile.core.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import kr.smobile.BaseApplication
import javax.inject.Singleton

@Singleton
@Component( modules = [ApplicationModule::class, AndroidInjectionModule::class, ActivityBuilderModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : ApplicationComponent
    }

    fun inject(application: BaseApplication)

}