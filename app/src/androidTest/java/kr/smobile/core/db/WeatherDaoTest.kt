package kr.smobile.core.db


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import kr.smobile.core.util.LiveDataTestUtil
import kr.smobile.core.util.TestUtil
import kr.smobile.vo.OpenWeatherResult
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStreamReader
import org.junit.Assert.assertThat

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest : DBTest() {

    /**
     * A JUnit Test Rule that swaps the background executor used by the Architecture
     * Components with a different one which executes each task synchronously.
    You can use this rule for your host side tests that use Architecture Components.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndLoadByCityId() {
        val result = createWeather()

        assertThat(result.cityName, `is`("London"))
        assertThat(result.cityId, `is`(2643743))
        assertThat(result.weatherItems[0].id, `is`(300))
        assertThat(result.weatherItems[0].main, `is`("Drizzle"))
        assertThat(result.weatherItems[0].description, `is`("light intensity drizzle"))

        //insert
        db.weatherDao().insert(result)

        //load
        val loaded = LiveDataTestUtil.getValue( liveData = db.weatherDao().getLatestWeatherByCityId(2643743))

        assertThat( loaded, notNullValue())
        assertThat( loaded.cityName, `is`("London"))
        assertThat( loaded.cityId, `is`(2643743))
        assertThat( loaded.weatherItems[0].id, `is`(300))
        assertThat( loaded.weatherItems[0].main, `is`("Drizzle"))
        assertThat( loaded.weatherItems[0].description, `is`("light intensity drizzle"))
        assertThat( loaded.coordindate.latitude, `is`(51.51))
        assertThat( loaded.coordindate.longitude, `is`(-0.13))
        assertThat( loaded.mainWeatherInfo.temperature, `is`(280.32))
        assertThat( loaded.mainWeatherInfo.atmosphericPressure, `is`(1012.0))
        assertThat( loaded.mainWeatherInfo.humidity, `is`(81))
        assertThat( loaded.mainWeatherInfo.temp_min, `is`(279.15))
        assertThat( loaded.mainWeatherInfo.temp_max, `is`(281.15))

    }

    @Test fun insertAndLoadByCityName() {

        val result = TestUtil.createWeather()

        //insert
        db.weatherDao().insert(result)

        //load
        val loaded = LiveDataTestUtil.getValue( liveData = db.weatherDao().getLatestWeatherByCityName("London"))

        assertThat( loaded, notNullValue())
        assertThat( loaded.cityName, `is`("London"))
        assertThat( loaded.cityId, `is`(2643743))
        assertThat( loaded.weatherItems[0].id, `is`(300))
        assertThat( loaded.weatherItems[0].main, `is`("Drizzle"))
        assertThat( loaded.weatherItems[0].description, `is`("light intensity drizzle"))
        assertThat( loaded.coordindate.latitude, `is`(51.51))
        assertThat( loaded.coordindate.longitude, `is`(-0.13))
        assertThat( loaded.mainWeatherInfo.temperature, `is`(280.32))
        assertThat( loaded.mainWeatherInfo.atmosphericPressure, `is`(1012.0))
        assertThat( loaded.mainWeatherInfo.humidity, `is`(81))
        assertThat( loaded.mainWeatherInfo.temp_min, `is`(279.15))
        assertThat( loaded.mainWeatherInfo.temp_max, `is`(281.15))
    }

    private fun createWeather() : OpenWeatherResult {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("weatherresponse.json")

        val gson = Gson()
        val openWeatherResult = gson.fromJson(InputStreamReader(inputStream,"UTF-8"), OpenWeatherResult::class.java)
        return openWeatherResult

    }
}