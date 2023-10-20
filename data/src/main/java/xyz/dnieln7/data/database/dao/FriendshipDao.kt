package xyz.dnieln7.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.dnieln7.data.database.model.FriendshipDbModel

@Dao
interface FriendshipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriendships(friendshipDbModels: List<FriendshipDbModel>)

    @Query("SELECT * FROM friendships")
    suspend fun getFriendships(): List<FriendshipDbModel>
}