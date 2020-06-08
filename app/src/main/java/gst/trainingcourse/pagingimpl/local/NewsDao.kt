package gst.trainingcourse.pagingimpl.local

import androidx.room.Dao
import androidx.room.Query
import gst.trainingcourse.pagingimpl.local.model.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM Article")
    fun getNews(): List<Article>
}