package kr.smobile.core.extension

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


inline fun <reified T : ViewModel> Activity.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProvider(this as FragmentActivity, factory)[T::class.java]
    vm.body()
    return vm
}
//inline fun Activity.showNoticeAlertDialog(func: NoticeDialogHelper.() -> Unit): AlertDialog =
//    NoticeDialogHelper(this).apply {
//        func()
//    }.create()