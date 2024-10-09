package com.example.takeahike.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface HikeDao {

    @Query("SELECT * FROM hikes WHERE id=(:id)")
    fun getHike(id: UUID): Hike

    @Query("SELECT * FROM hikes")
    fun getHikes(): Flow<List<Hike>>

    @Query("DELETE FROM hikes")
    fun deleteAllHikes()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createHike(hike: Hike)
}