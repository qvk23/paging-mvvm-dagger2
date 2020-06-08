package gst.trainingcourse.pagingimpl.remote

import gst.trainingcourse.pagingimpl.local.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(("/v2/everything?q=sports"))
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response
}