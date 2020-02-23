package kr.smobile.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import kr.smobile.R
import kr.smobile.core.di.ViewModelFactory
import kr.smobile.core.platform.BaseActivity

import javax.inject.Inject


class MainActivity : BaseActivity() {




    //private val viewModel by viewModels { viewModelFactory }
    //val viewmodel by viewModels<MainViewModel> { SavedStateViewModelFactory(this) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
