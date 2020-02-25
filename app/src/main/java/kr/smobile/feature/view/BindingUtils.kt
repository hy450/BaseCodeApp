package kr.smobile.feature.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.util.concurrent.atomic.AtomicBoolean

@BindingAdapter("loadWeatherIcon")
fun loadWeatherIcon(view: ImageView, url: String?) {
    url ?: return

    Glide.with(view)
        .load("http://openweathermap.org/img/wn/${url}@2x.png")
        .into(view)
}

private fun makeOption(radiusPixel: Int, placeholder: Drawable) : RequestOptions {
    return if(radiusPixel > 0) {
        RequestOptions()
            .transform(CenterCrop(),  RoundedCorners(radiusPixel))
            .placeholder(placeholder)
            .error(placeholder)

    } else {
        RequestOptions()
            .transform(CenterCrop())
            .placeholder(placeholder)
            .error(placeholder)
    }
}

/**
 * binding double click prevent
 */
@BindingAdapter(value = ["OnSingleClick","clickDelay"], requireAll = false)
fun View.setOnSingleClickListener(clickListener: View.OnClickListener?, clickDelay: Long) {
    //debug("setOnSingleClickListener $clickDelay")
    var _clickDelay = clickDelay
    if(_clickDelay == 0L) { //값을 입력하지 않으면 0 이므로 기본값으로 변경.
        _clickDelay = 1000
    }
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it,_clickDelay))
    } ?: setOnClickListener(null)
}

class OnSingleClickListener(
    private val clickListener: View.OnClickListener,
    private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                clickListener.onClick(v)
            }
        }
    }
}