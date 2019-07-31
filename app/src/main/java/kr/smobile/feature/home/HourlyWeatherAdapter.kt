package kr.smobile.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kr.smobile.R
import kr.smobile.vo.OpenForeCastWeather

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

            Glide.with(itemView.context)
                .load("http://openweathermap.org/img/wn/${item.weatherItems[0].icon}@2x.png")
                .into(imgView)

        }

    }
}