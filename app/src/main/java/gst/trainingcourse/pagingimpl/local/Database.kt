package gst.trainingcourse.pagingimpl.local

import androidx.room.Database
import androidx.room.RoomDatabase
import gst.trainingcourse.pagingimpl.local.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}