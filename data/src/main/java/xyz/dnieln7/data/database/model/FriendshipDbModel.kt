package xyz.dnieln7.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friendships")
data class FriendshipDbModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "username")
    val username: String,
)
