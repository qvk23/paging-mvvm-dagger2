package gst.trainingcourse.pagingimpl

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import gst.trainingcourse.pagingimpl.local.model.Article
import gst.trainingcourse.pagingimpl.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    private val newsRepository: INewsRepository
) : ViewModel() {
    private var currentSearchResult: Flow<PagingData<Article>>? = null
    fun getNews(): Flow<PagingData<Article>> {
        val lastResult = currentSearchResult
        val result = newsRepository.getNews().cachedIn(viewModelScope)
        currentSearchResult = result
        return result
    }
}