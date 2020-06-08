package gst.trainingcourse.pagingimpl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gst.trainingcourse.pagingimpl.repository.INewsRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val newsRepository: INewsRepository
) : ViewModel() {
    val newsList by lazy {
        newsRepository.getNews(viewModelScope)
    }
}