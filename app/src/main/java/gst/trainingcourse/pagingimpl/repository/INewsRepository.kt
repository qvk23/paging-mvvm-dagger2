package gst.trainingcourse.pagingimpl.repository

import androidx.paging.PagingData
import gst.trainingcourse.pagingimpl.local.model.Article
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getNews(): Flow<PagingData<Article>>
}