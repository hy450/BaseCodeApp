package kr.smobile.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.smobile.vo.ForeCastResult
import kr.smobile.vo.OpenWeatherResult

@Database(
    entities = [
        OpenWeatherResult::class,
        ForeCastResult::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BaseCodeDb : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}