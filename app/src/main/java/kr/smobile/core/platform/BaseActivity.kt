package kr.smobile.core.platform

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kr.smobile.sample.R
import kr.smobile.core.di.DaggerFragmentFactory
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(){

    @Inject
    internal lateinit var fragmentFactory: DaggerFragmentFactory


    protected val compositeDisposables = CompositeDisposable()

    private var currAlertDialog: AlertDialog? = null
    private var loadingProgress: AppCompatDialog? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory

    }

    override fun onDestroy() {
        compositeDisposables.clear()
        super.onDestroy()
    }

    // popup & toast
    open fun showAlertPopup(event: VmEvent<AlertEvent>?) {
        event?.getContentIfNotHandled()?.let {
            when (it) {
                is AlertEvent.PopupEvent -> showAlertPopup(it.data)
                is AlertEvent.ToastEvent -> showToast(it.data)
            }
        }
    }



    protected fun showAlertPopup( eventData: PopupEventData) {
//        currAlertDialog = showNoticeAlertDialog {
//            isBackGroundTransparent = false
//            title = eventData.title
//            positiveBtnClickListener(eventData.okBtnClick)
//            negativeBtnClickListener(eventData.cancelBtnClick)
//        }
        currAlertDialog?.show()
    }

    protected fun showToast( toastData: ToastEventData) {
        Toast.makeText(this, toastData.resStr ,toastData.time).show()
    }

    protected fun isShowingAlertDialog() = currAlertDialog?.isShowing == true


    fun showLoading(isShow: Boolean) {
        if( isFinishing ) return //activity 가 종료중이면 return 처리

        when(isShow) {
            true -> {
                if (loadingProgress == null) {
                    loadingProgress = AppCompatDialog(this).apply {
                        setCancelable(false)
                        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        setContentView(R.layout.progress_loading)
                    }
                    loadingProgress?.show()
                }
            }
            else -> {
                loadingProgress?.dismiss()
                loadingProgress = null
            }
        }
    }

}