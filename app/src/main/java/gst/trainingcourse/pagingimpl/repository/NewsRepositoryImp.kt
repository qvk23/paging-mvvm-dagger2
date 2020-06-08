package gst.trainingcourse.pagingimpl.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import gst.trainingcourse.pagingimpl.NewsDataSourceFactory
import gst.trainingcourse.pagingimpl.local.NewsDao
import gst.trainingcourse.pagingimpl.local.model.Article
import gst.trainingcourse.pagingimpl.remote.ApiService
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    private val newsApi: ApiService,
    private val newsDao: NewsDao
) : INewsRepository {
    override fun getNews(scope: CoroutineScope): LiveData<PagedList<Article>> {
        val sourceFactory = NewsDataSourceFactory(newsApi, newsDao, scope)
        val config =
            PagedList.Config.Builder()
                .setInitialLoadSizeHint(100)
                .setEnablePlaceholders(true)
                .build()
        return LivePagedListBuilder(sourceFactory, config).build()
    }
}