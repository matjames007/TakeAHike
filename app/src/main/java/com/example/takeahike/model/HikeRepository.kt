package com.example.takeahike.model

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class HikeRepository(private val hikeDao: HikeDao) {

    val allHikes: Flow<List<Hike>> = hikeDao.getHikes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(hike:Hike) {
        hikeDao.createHike(hike)
    }
}