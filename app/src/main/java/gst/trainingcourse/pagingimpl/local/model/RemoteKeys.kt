package gst.trainingcourse.pagingimpl.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey
    val title: String,
    val prevKey: Int?,
    val nextKey: Int?
)