package com.zajko04.virus.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [AlarmEntity::class],version = 1)
abstract class AlarmEntityDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmEntityDAO

    companion object{
        @Volatile
        private var INSTANCE: AlarmEntityDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AlarmEntityDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmEntityDatabase::class.java,
                    "my_database"
                ).addCallback(AlarmDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class AlarmDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { alarmEntityDatabase ->
                scope.launch {
                    val list: List<AlarmEntity> = listOf(
                        AlarmEntity("Title","Contents","Time")
                    )

                    Thread(Runnable {
                        alarmEntityDatabase.alarmDao().insertAllAlarms(list)
                    }).start()
                }
            }
        }
    }
}