package kr.smobile.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenWeatherResult

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherResult: OpenWeatherResult)

    @Query("SELECT * FROM OpenWeatherResult WHERE cityName = :cityName ORDER BY datetime DESC LIMIT 1")
    fun getLatestWeatherByCityName(cityName: String): Maybe<OpenWeatherResult>

    @Query("SELECT * FROM OpenWeatherResult WHERE cityId = :cityId ORDER BY datetime DESC LIMIT 1")
    fun getLatestWeatherByCityId(cityId: Int): Maybe<OpenWeatherResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(foreCastResult: ForeCastResult)

    @Query("SELECT * FROM ForeCastResult WHERE cityId = :cityId ORDER BY createAt DESC LIMIT 1")
    fun getForecastByCityId(cityId: Int): Maybe<ForeCastResult>

    @Query("SELECT * FROM ForeCastResult WHERE cityName = :cityName ORDER BY createAt DESC LIMIT 1")
    fun getForecastByCityName(cityName: Int): Maybe<ForeCastResult>
}