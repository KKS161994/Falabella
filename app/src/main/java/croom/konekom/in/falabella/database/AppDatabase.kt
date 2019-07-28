package croom.konekom.`in`.falabella.database


import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import croom.konekom.`in`.falabella.model.Beer

/***
 * Created By Kartikey Kumar Srivastava
 */

//Database to store values locally
@Database(entities = [Beer::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun beerDao(): BeerEntryDao

    companion object {
        private var instance: AppDatabase? = null

        private val DB_NAME = "TestTask"

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}