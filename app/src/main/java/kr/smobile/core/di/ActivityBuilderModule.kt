package kr.smobile.core.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kr.smobile.feature.MainActivity

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity() : MainActivity

    @Module(includes = [FragmentBuildersModule::class])
    internal abstract class MainModule
}