package kr.smobile.core.di

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import kr.smobile.feature.addCity.AddCityFragment
import kr.smobile.feature.home.HomeFragment

@Module
abstract class FragmentBuildersModule {

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun bindHomeFragment(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(AddCityFragment::class)
    abstract fun bindAddCityFragment(fragment: AddCityFragment): Fragment

}