package com.example.takeahike

import android.app.Application
import com.example.takeahike.model.HikeRepository
import com.example.takeahike.model.HikeRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class HikeApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { HikeRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { HikeRepository(database.hikeDao()) }
}