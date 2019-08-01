package kr.smobile.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kr.smobile.R
import kr.smobile.core.extension.debug
import kr.smobile.core.extension.getTimeStr
import kr.smobile.vo.OpenForeCastWeather
import java.util.*

class HourlyWeatherAdapter : RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>() {

    var hourlyWeatherInfos = listOf<OpenForeCastWeather>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_hourly_weather_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = hourlyWeatherInfos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hourlyWeatherInfos[position])
    }

    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        fun bind(item: OpenForeCastWeather) {

            val imgView = itemView.findViewById<ImageView>(R.id.imageView)
            val hourTxt = itemView.findViewById<TextView>(R.id.hourTxt)
            val temperature = itemView.findViewById<TextView>(R.id.temperature)

            item.weatherItems.firstOrNull()?.let {
                Glide.with(itemView.context)
                    .load("http://openweathermap.org/img/wn/${it.icon}@2x.png")
                    .into(imgView)
            }

            val date = Date(item.datetime*1000)
            hourTxt.text = date.getTimeStr("a h:mm")
            temperature.text = itemView.resources.getString(R.string.home_celius_temperature,item.mainWeatherInfo.temperature.toInt().toString())

        }

    }
}