package gst.trainingcourse.pagingimpl.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gst.trainingcourse.pagingimpl.local.RemoteKeysDao
import gst.trainingcourse.pagingimpl.local.AppDatabase
import gst.trainingcourse.pagingimpl.local.NewsDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase =
        AppDatabase.getInstance(application)

    @Provides
    @Singleton
    internal fun provideNewsDao(appDatabase: AppDatabase): NewsDao = appDatabase.newsDao()

    @Provides
    @Singleton
    internal fun provideRemoteKeysDao(appDatabase: AppDatabase): RemoteKeysDao = appDatabase.remoteKeyDao()

}