package gst.trainingcourse.pagingimpl

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import gst.trainingcourse.pagingimpl.local.AppDatabase
import gst.trainingcourse.pagingimpl.local.model.Article
import gst.trainingcourse.pagingimpl.local.model.RemoteKeys
import gst.trainingcourse.pagingimpl.remote.ApiService
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase
) : RemoteMediator<Int, Article>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> return MediatorResult.Success(true)

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }
        try {
            val data = apiService.getNews(page, state.config.pageSize)
            val endOfPaginationReached = data.articles.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeyDao().clearRemoteKeys()
                    database.newsDao().clearNews()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = data.articles.map {
                    RemoteKeys(title = it.title, prevKey = prevKey, nextKey = nextKey)
                }
                Log.d(
                    "MyLogTag",
                    "class: NewsRemoteMediator func: load: (69) ${state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                        ?.let { database.remoteKeyDao().remoteKeysNewsTitle(it.title) }}"
                )
                database.remoteKeyDao().insertAll(keys)
                val listKey = database.remoteKeyDao().remoteKeysNews()
                Log.d("MyLogTag", "class: NewsRemoteMediator func: load: (75) $listKey")
                database.newsDao().insertAll(data.articles)
                Log.d("MyLogTag",
                    "class: NewsRemoteMediator func: load: (78) ${state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                        ?.let { database.remoteKeyDao().remoteKeysNewsTitle(it.title) }}"
                )
            }
            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { news ->
            // Get the remote keys of the last item retrieved
            database.remoteKeyDao().remoteKeysNewsTitle(news.title)
        }
    }

//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteKeys? {
//        // Get the first page that was retrieved, that contained items.
//        // From that first page, get the first item
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { news ->
//                // Get the remote keys of the first items retrieved
//                database.remoteKeyDao().remoteKeysNewsTitle(news.title)
//            }
//    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { newsTitle ->
                database.remoteKeyDao().remoteKeysNewsTitle(newsTitle)
            }
        }
    }
}