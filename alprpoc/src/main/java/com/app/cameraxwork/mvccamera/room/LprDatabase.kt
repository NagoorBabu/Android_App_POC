package com.app.cameraxwork.mvccamera.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LprCacheEntity::class], version = 1)
abstract class LprDatabase: RoomDatabase() {

    abstract fun lprDao(): LprDao

    companion object {
        val DATABASE_NAME: String = "lpr_db"
    }
}
