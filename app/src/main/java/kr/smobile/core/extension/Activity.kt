package kr.smobile.core.extension

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders


inline fun <reified T : ViewModel> Activity.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this as FragmentActivity, factory)[T::class.java]
    vm.body()
    return vm
}