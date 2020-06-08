package gst.trainingcourse.pagingimpl.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import gst.trainingcourse.pagingimpl.local.model.Article
import gst.trainingcourse.pagingimpl.remote.Result
import kotlinx.coroutines.CoroutineScope

interface INewsRepository {
    fun getNews(scope: CoroutineScope): LiveData<PagedList<Article>>
}