package gst.trainingcourse.pagingimpl.local.model


import com.google.gson.annotations.SerializedName

data class Response(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)