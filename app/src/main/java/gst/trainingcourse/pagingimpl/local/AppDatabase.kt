package gst.trainingcourse.pagingimpl.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gst.trainingcourse.pagingimpl.local.model.Article
import gst.trainingcourse.pagingimpl.local.model.RemoteKeys

@Database(entities = [Article::class, RemoteKeys::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun remoteKeyDao(): RemoteKeysDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "News.db"
                ).fallbackToDestructiveMigrationOnDowngrade()
                    .allowMainThreadQueries()
                    .addMigrations()
                    .build()
                    .also { INSTANCE = it }
            }


    }
}