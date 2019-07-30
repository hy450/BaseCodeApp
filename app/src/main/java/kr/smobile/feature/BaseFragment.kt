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
        // onCreate 에서 생성하지 않고 onViewCreated 에서 생성해야함.
        // 왜냐하면 android injection lifecycle listener 로 수행하고 있기 때문.
        viewModel = createViewModel()
    }
}