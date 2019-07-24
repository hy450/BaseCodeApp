package kr.smobile.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kr.smobile.core.api.WeatherService
import kr.smobile.core.db.WeatherDao
import kr.smobile.core.util.InstantAppExecutors
import kr.smobile.data.WeatherRepository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class WeatherRepositoryTest {

    private val weatherDao = mock(WeatherDao::class.java)
    private val weatherService = mock(WeatherService::class.java)

    private val repo = WeatherRepository(InstantAppExecutors(),weatherService,weatherDao)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

}