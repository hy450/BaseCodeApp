package kr.smobile.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kr.smobile.feature.MainActivity
import kr.smobile.feature.MainViewModel
import kr.smobile.feature.addCity.AddCityViewModel
import kr.smobile.feature.home.HomeViewModel


@Module
abstract class ViewModelModule {

//    @Binds
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModelAssistedFactory<out ViewModel>
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(HomeViewModel::class)
//    abstract fun bindHomeViewModel(viewModel: HomeViewModel) : ViewModelAssistedFactory<out ViewModel>
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(AddCityViewModel::class)
//    abstract fun bindAddCityViewModel(viewModel: AddCityViewModel) : ViewModelAssistedFactory<out ViewModel>
//
//    @Binds
//    abstract fun bindSavedStateRegistryOwner(mainActivity: MainActivity): SavedStateRegistryOwner?


}