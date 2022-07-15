package com.app.cameraxwork.mvccamera.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface LprDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lprEntity: LprCacheEntity): Long

    @Query("SELECT * FROM lpr_records")
    suspend fun get(): List<LprCacheEntity>

    @Query("SELECT * FROM lpr_records WHERE id=:id")
    suspend fun get(id: String): LprCacheEntity
}