package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.models.AccountProperties
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.models.BlogPost

@Database(entities = [AuthToken::class, AccountProperties::class, BlogPost::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    abstract fun getAccountPropertiesDao(): AccountPropertiesDao

    abstract fun getBlogPostDao(): BlogPostDao

    companion object {

        const val DATABASE_NAME = "app_db"
    }
}