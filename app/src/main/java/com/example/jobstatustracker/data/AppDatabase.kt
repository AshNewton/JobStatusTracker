package com.example.jobstatustracker.data

import android.content.Context
import androidx.room.*

@Database(
    entities = [JobApplication::class, JobApplicationEntry::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): JobApplicationDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "application_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}