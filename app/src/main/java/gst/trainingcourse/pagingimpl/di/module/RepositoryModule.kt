package gst.trainingcourse.pagingimpl.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gst.trainingcourse.pagingimpl.repository.INewsRepository
import gst.trainingcourse.pagingimpl.repository.NewsRepositoryImp
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideNewsRepository(newsRepositoryImp: NewsRepositoryImp): INewsRepository
}
