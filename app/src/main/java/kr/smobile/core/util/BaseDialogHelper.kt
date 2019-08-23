package kr.smobile.core.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog

abstract class BaseDialogHelper {

    abstract val dialogView: View
    abstract val builder: AlertDialog.Builder

    //  required bools
    open var cancelable: Boolean = true // 기본이 back key로 취소 가능
    open var isBackGroundTransparent: Boolean = true

    //  dialog
    open var dialog: AlertDialog? = null

    //  dialog create
    open fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        dialog?.setCanceledOnTouchOutside(false) // 기본이 영역외 터치시  팝업 안닫힘.

        //  very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog!!
    }

    //  cancel listener
    open fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnCancelListener {
            func()
        }

    open fun onDismissListener(func: () -> Unit) : AlertDialog.Builder? =
        builder.setOnDismissListener {
            func()
        }

    open fun onBackKeyListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK)
                func()
            true
        }

}