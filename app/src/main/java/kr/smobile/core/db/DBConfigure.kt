package kr.smobile.core.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

object DBConfigure {
    var rdc: RoomDatabase.Callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            //defaultDbInsert(db)
        }

    }

    /**
     * Database Migration 정의 부분
     *
     * 예시>
     * val MIGRATION_3_4: Migration = object : Migration(3, 4) {
     *  override fun migrate(db: SupportSQLiteDatabase) {
     *      db.execSQL("DROP TABLE `FamilyInfo`")
     *      db.execSQL("CREATE TABLE IF NOT EXISTS `FamilyInfo` (`issync` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `insert_date` INTEGER NOT NULL,
     *      `name` TEXT NOT NULL, `type` INTEGER NOT NULL, `gender` INTEGER NOT NULL, `birthDay` INTEGER NOT NULL, `family_cd` INTEGER NOT NULL, `profilePath` TEXT NOT NULL)")
     *  }
     * }
     */

}