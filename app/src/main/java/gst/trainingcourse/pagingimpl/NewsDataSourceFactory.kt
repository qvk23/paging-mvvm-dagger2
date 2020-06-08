package gst.trainingcourse.pagingimpl

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import gst.trainingcourse.pagingimpl.local.NewsDao
import gst.trainingcourse.pagingimpl.local.model.Article
import gst.trainingcourse.pagingimpl.remote.ApiService
import gst.trainingcourse.pagingimpl.repository.INewsRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class NewsDataSourceFactory @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val coroutineScope: CoroutineScope
) : DataSource.Factory<Int, Article>() {
    private val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()
    override fun create(): DataSource<Int, Article> {
        val newsDataSource = NewsDataSource(apiService, newsDao, coroutineScope)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}