package kr.smobile.core.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kr.smobile.feature.home.HomeFragment

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun bindHomeFragment() : HomeFragment

}