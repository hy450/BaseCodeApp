package kr.smobile.feature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment<T: BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: T


    abstract fun createViewModel(): T


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // onCreate에서 생성하지 않고 onViewCreated에서 생성해야함.
        viewModel = createViewModel()
    }
}