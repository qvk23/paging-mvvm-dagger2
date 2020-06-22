package gst.trainingcourse.pagingimpl.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import gst.trainingcourse.pagingimpl.NewsRemoteMediator
import gst.trainingcourse.pagingimpl.local.AppDatabase
import gst.trainingcourse.pagingimpl.local.model.Article
import gst.trainingcourse.pagingimpl.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    private val newsApi: ApiService,
    private val database: AppDatabase
) : INewsRepository {
    override fun getNews(): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(30),
        remoteMediator = NewsRemoteMediator(newsApi, database)
    ) {
        database.newsDao().getNews()
    }.flow
}