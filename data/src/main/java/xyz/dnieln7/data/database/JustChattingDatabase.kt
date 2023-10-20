package xyz.dnieln7.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.dnieln7.data.database.dao.FriendshipDao
import xyz.dnieln7.data.database.model.FriendshipDbModel

@Database(
    entities = [FriendshipDbModel::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class JustChattingDatabase : RoomDatabase() {
    abstract fun friendshipDao(): FriendshipDao

    companion object {
        fun build(context: Context): JustChattingDatabase {
            return Room.databaseBuilder(
                context,
                JustChattingDatabase::class.java,
                "just_chatting_data"
            ).build()
        }
    }
}
