package kr.smobile.core.db


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import kr.smobile.vo.OpenWeatherResult
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStreamReader
import org.junit.Assert.assertThat

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest : DBTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndLoad() {
        val result = createWeather()

        assertThat(result.cityName, `is`("London"))

    }

    private fun createWeather() : OpenWeatherResult {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("weatherresponse.json")

        val gson = Gson()
        val openWeatherResult = gson.fromJson(InputStreamReader(inputStream,"UTF-8"), OpenWeatherResult::class.java)
        return openWeatherResult

    }
}