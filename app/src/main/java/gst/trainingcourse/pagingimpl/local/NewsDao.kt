package gst.trainingcourse.pagingimpl.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gst.trainingcourse.pagingimpl.local.model.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM Article")
    fun getNews(): PagingSource<Int, Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<Article>)

    @Query("DELETE FROM article")
    suspend fun clearNews()
}