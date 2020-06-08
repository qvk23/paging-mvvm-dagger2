package gst.trainingcourse.pagingimpl.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import gst.trainingcourse.pagingimpl.repository.INewsRepository
import gst.trainingcourse.pagingimpl.repository.NewsRepositoryImp
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideNewsRepository(newsRepositoryImp: NewsRepositoryImp): INewsRepository

}
