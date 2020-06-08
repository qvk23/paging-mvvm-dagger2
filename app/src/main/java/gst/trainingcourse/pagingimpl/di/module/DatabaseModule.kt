package gst.trainingcourse.pagingimpl.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import gst.trainingcourse.pagingimpl.local.Database
import gst.trainingcourse.pagingimpl.local.NewsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): Database =
        Room.databaseBuilder(application, Database::class.java, "Database")
            .allowMainThreadQueries().build()

    @Provides
    @Singleton
    internal fun provideNewsDao(database: Database): NewsDao = database.newsDao()

}