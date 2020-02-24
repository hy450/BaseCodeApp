package kr.smobile.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kr.smobile.BR
import kr.smobile.R
import kr.smobile.core.extension.debug
import kr.smobile.core.extension.getTimeStr
import kr.smobile.databinding.HomeHourlyWeatherItemBinding
import kr.smobile.vo.OpenForeCastWeather
import java.util.*

data class HourlyWeatherUiModel(
    val dateStr: String,
    val currDegree: String,
    val iconUrl: String? = null
) {
    companion object {
        fun convert(item: OpenForeCastWeather): HourlyWeatherUiModel {
            val dateStr = Date(item.datetime*1000).getTimeStr("a h:mm")
            val iconUrl: String? = item.weatherItems.firstOrNull()?.icon
            return HourlyWeatherUiModel(
                dateStr,
                item.mainWeatherInfo.dispTemperatureStr,
                iconUrl
            )
        }
    }
}

class HourlyWeatherAdapter : RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>() {

    var hourlyWeatherInfos = listOf<HourlyWeatherUiModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HomeHourlyWeatherItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = hourlyWeatherInfos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hourlyWeatherInfos[position])
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HourlyWeatherUiModel) {
            binding.setVariable(BR.weather,item)
            binding.executePendingBindings()
        }

    }
}