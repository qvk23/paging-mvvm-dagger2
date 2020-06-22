package gst.trainingcourse.pagingimpl

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var getListJob: Job? = null
    private val newsListAdapter = NewsListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        retry_button.setOnClickListener { newsListAdapter.retry() }
    }

    private fun initAdapter() {

        recycler_view.adapter = newsListAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { newsListAdapter.retry() },
            footer = LoadStateAdapter { newsListAdapter.retry() }
        )
        newsListAdapter.addLoadStateListener { it ->
            recycler_view.isVisible = it.refresh is LoadState.NotLoading
            progress_bar.isVisible = it.refresh is LoadState.Loading
            retry_button.isVisible = it.refresh is LoadState.Error
            val errorState = it.source.append as? LoadState.Error // paging source
                ?: it.source.prepend as? LoadState.Error // paging source
//                ?: it.append as? LoadState.Error
//                ?: it.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(this, "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_LONG).show()
            }
        }
        getListJob?.cancel()
        getListJob = lifecycleScope.launch {
            viewModel.getNews().collectLatest {
                newsListAdapter.submitData(it)
            }
        }
    }
}