package kr.smobile.core.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kr.smobile.core.util.mock
import kr.smobile.data.WeatherRepository
import kr.smobile.feature.home.HomeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.hamcrest.*
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.ArgumentMatchers
import org.mockito.internal.matchers.NotNull

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val weatherRepo = mock(WeatherRepository::class.java)
    private val homeViewModel = HomeViewModel(weatherRepo)

    @Test
    fun testNotSelectedCityId() {

        assertThat( homeViewModel.currFavorCityId, notNullValue() )
        assertThat( homeViewModel.favorCityWeatherRepo, notNullValue())
        assertThat( homeViewModel.favorHourlyWeatherRepo, notNullValue())

        verify(weatherRepo,never()).loadLatesWeatherByCityId(anyInt())
        verify(weatherRepo,never()).loadForeCastWeather(anyInt())

    }

    @Test
    fun testDontFetchWithOutObservers() {
        homeViewModel.setCurrFavoriteCity(1835848)
        verify(weatherRepo,never()).loadLatesWeatherByCityId(anyInt())
    }

    @Test
    fun testFetchWithObservers() {

        homeViewModel.setCurrFavoriteCity(1835848)
        homeViewModel.favorCityWeatherRepo.observeForever(mock())
        homeViewModel.favorHourlyWeatherRepo.observeForever(mock())

        verify(weatherRepo).loadLatesWeatherByCityId(anyInt())
        verify(weatherRepo).loadForeCastWeather(anyInt())
    }

    @Test
    fun testFetchsWithObservers() {

        homeViewModel.favorCityWeatherRepo.observeForever(mock())
        homeViewModel.favorHourlyWeatherRepo.observeForever(mock())

        homeViewModel.setCurrFavoriteCity(1835848)
        homeViewModel.setCurrFavoriteCity(1835847)


        verify(weatherRepo).loadLatesWeatherByCityId(1835848)
        verify(weatherRepo).loadLatesWeatherByCityId(1835847)

        verify(weatherRepo).loadForeCastWeather(1835848)
        verify(weatherRepo).loadForeCastWeather(1835847)
    }
}