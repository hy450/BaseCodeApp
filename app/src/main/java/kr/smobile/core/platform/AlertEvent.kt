package kr.smobile.core.platform

import android.content.res.Resources
import android.widget.Toast

sealed class AlertEvent {

    data class PopupEvent( val data : PopupEventData ) : AlertEvent()
    data class ToastEvent( val data : ToastEventData ) : AlertEvent()

}

data class PopupEventData (
    var popupMessage: String = "",
    var title: String? = null,
    var okBtnTitle: String? = null,
    var okBtnClick: (() -> Unit)? = null,
    var cancelBtnTitle: String? = null,
    var cancelBtnClick: (() -> Unit)? = null
) {

    var popupMessageResId : Int = 0
        set(value) {
            field = value
            if(popupMessageResId != 0) {
                popupMessage = try { Resources.getSystem().getString(value) } catch (e: Exception) { "" }
            }
        }

    var titleResId : Int = 0
        set(value) {
            field = value
            if (titleResId != 0) {
                popupMessage = try { Resources.getSystem().getString(value) } catch (e: Exception) { "" }
            }
        }
}

data class ToastEventData(
    var resStr: String = "" ,
    var time : Int = Toast.LENGTH_SHORT
) {
    var resStrId : Int = 0
        set(value) {
            field = value
            if (resStrId !=0 ) {
                resStr = try { Resources.getSystem().getString(value) } catch (e: Exception) { "" }
            }
        }

}