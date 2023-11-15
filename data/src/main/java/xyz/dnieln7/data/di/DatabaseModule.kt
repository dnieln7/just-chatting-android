package xyz.dnieln7.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.dnieln7.data.database.JustChattingDatabase
import xyz.dnieln7.data.database.dao.FriendshipDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideJustChattingDatabase(@ApplicationContext context: Context): JustChattingDatabase {
        return JustChattingDatabase.build(context)
    }

    @Provides
    @Singleton
    fun provideFriendshipDao(justChattingDatabase: JustChattingDatabase): FriendshipDao {
        return justChattingDatabase.friendshipDao()
    }
}