package com.app.cameraxwork.mvccamera.di

import android.content.Context
import androidx.room.Room
import com.app.cameraxwork.mvccamera.room.LprDao
import com.app.cameraxwork.mvccamera.room.LprDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideLprDb(@ApplicationContext context: Context): LprDatabase {
        return Room.databaseBuilder(
            context,
            LprDatabase::class.java,
            LprDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideLprDao(lprDatabase: LprDatabase): LprDao {
        return lprDatabase.lprDao()
    }
}