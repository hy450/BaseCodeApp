package kr.smobile.core.platform

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<T: BaseViewModel> : Fragment() , CoroutineScope {

    protected abstract val viewModel: T

    protected val compositeDisposables = CompositeDisposable()

    private val showAlertEventObserver = Observer<VmEvent<AlertEvent>> { showAlertPopup(it) }
    protected val showLoadingObserver = Observer<Boolean> { updateLoadingIndicator(it) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // onCreate 에서 생성하지 않고 onViewCreated 에서 생성해야함.
        // 왜냐하면 android injection lifecycle listener 로 수행하고 있기 때문.
        //viewModel = createViewModel()

        //show alert popup observer
        viewModel.showAlertEvent.observe(viewLifecycleOwner,showAlertEventObserver)
        viewModel.loadingEvent.observe(viewLifecycleOwner,showLoadingObserver)

    }

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroyView() {
        compositeDisposables.clear()
        job.cancel()
        super.onDestroyView()
    }

    //popup & toast
    open fun showAlertPopup(event: VmEvent<AlertEvent>?) {
        val parent = activity as? BaseActivity
        parent?.showAlertPopup(event)
    }

    protected fun updateLoadingIndicator( isShow: Boolean) {
        val parent = activity as? BaseActivity
        parent?.showLoading(isShow)

    }
}