package kr.smobile.core.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
    ],
    version = 1,
    exportSchema = false
)
abstract class BaseCodeDb : RoomDatabase() {
}