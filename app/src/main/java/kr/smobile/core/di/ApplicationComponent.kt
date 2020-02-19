package kr.smobile.core.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kr.smobile.BaseApplication
import kr.smobile.feature.MainViewModel
import kr.smobile.feature.addCity.AddCityViewModel
import kr.smobile.feature.home.HomeViewModel
import javax.inject.Singleton

@Singleton
@Component( modules = [ApplicationModule::class,
    AndroidInjectionModule::class,
    ActivityBuilderModule::class,
    AndroidSupportInjectionModule::class,
    ViewModelAssistedFactoriesModule::class])
interface ApplicationComponent : AndroidInjector<BaseApplication>  {

    @Component.Factory
    interface Factory: AndroidInjector.Factory<BaseApplication>

}