package kr.smobile.core.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kr.smobile.feature.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindMainActivity() : MainActivity
}