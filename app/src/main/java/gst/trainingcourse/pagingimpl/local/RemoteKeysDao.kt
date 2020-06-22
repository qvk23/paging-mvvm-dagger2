package gst.trainingcourse.pagingimpl.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gst.trainingcourse.pagingimpl.local.model.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remotekeys WHERE title = :title")
    suspend fun remoteKeysNewsTitle(title: String): RemoteKeys?

    @Query("SELECT * FROM remotekeys")
    suspend fun remoteKeysNews(): List<RemoteKeys>

    @Query("DELETE FROM remotekeys")
    suspend fun clearRemoteKeys()
}