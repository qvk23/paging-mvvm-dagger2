package gst.trainingcourse.pagingimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import gst.trainingcourse.pagingimpl.local.NewsDao
import gst.trainingcourse.pagingimpl.local.model.Article
import gst.trainingcourse.pagingimpl.remote.ApiService
import gst.trainingcourse.pagingimpl.remote.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsDataSource @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Article>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        fetchData(params.key, params.requestedLoadSize) {
            callback.onResult(it, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        fetchData(params.key, params.requestedLoadSize) {
            callback.onResult(it, params.key - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<Article>) -> Unit) {
        scope.launch(Dispatchers.IO) {
            val response = apiService.getNews(page, pageSize)
            callback(response.articles)
        }
    }
}