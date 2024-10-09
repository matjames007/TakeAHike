package com.example.takeahike.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Hike::class), version = 1, exportSchema = false)
abstract class HikeRoomDatabase: RoomDatabase() {

    abstract fun hikeDao(): HikeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: HikeRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): HikeRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HikeRoomDatabase::class.java,
                    "hike_database"
                )
                    .addCallback(HikeDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class HikeDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.hikeDao())
                }
            }
        }

        suspend fun populateDatabase(hikeDao: HikeDao) {
            // Delete all content here.
            hikeDao.deleteAllHikes()

            // Add sample words.

            hikeDao.createHike(Hike(category = "Mountains", distance = 9.3, difficulty = "Hard",
                location = "KGN, JAM", name = "Blue Mountains", description="The Blue Mountain " +
                        "Peak Trail is the route to Jamaica's highest point, which sits at " +
                        "2,256 metres (7,401 ft.) The trail covers a distance of 9.3 km " +
                        "(5.8 miles) over steep terrain and is an intense 4-hour hike to the summit."))
            hikeDao.createHike(Hike(category = "Sea", distance = 4.3, difficulty = "Easy",
                location = "St. Mary, JAM", name = "Robin's Bay", description="Jamaica, known for its " +
                        "lush landscapes and natural wonders, holds a hidden gem that few have " +
                        "discovered – the elusive Mingo Waterfall. Introduced to me by my " +
                        "amazing Canadian friend Simone, this captivating waterfall lies on the " +
                        "Green Castle Estate, a 1600-acre ecological haven nestled between the " +
                        "Blue Mountains and the Caribbean Sea on Jamaica's North Coast."))
            hikeDao.createHike(Hike(category = "Rainforest", distance = 3.3, difficulty = "Medium",
                location = "Portland, JAM", name = "Nonsuch Falls", description="Experience one " +
                        "of Jamaica’s tallest water fall on a hike through nature that " +
                        "surrounds you with a vast biodiversity of Jamaican rain forest . " +
                        "Your intense journey begins with an experienced guide who will take " +
                        "you through the real wonders of nature."))

        }
    }
}