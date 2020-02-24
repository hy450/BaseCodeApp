package kr.smobile.feature.view

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

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