package xyz.dnieln7.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.dnieln7.data.database.dao.FriendshipDao
import xyz.dnieln7.data.repository.DefaultAuthRepository
import xyz.dnieln7.data.repository.DefaultFriendshipRepository
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.domain.repository.AuthRepository
import xyz.dnieln7.domain.repository.FriendshipRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        justChattingApiService: JustChattingApiService,
        resourceProvider: ResourceProvider,
    ): AuthRepository {
        return DefaultAuthRepository(justChattingApiService, resourceProvider)
    }

    @Provides
    @Singleton
    fun provideFriendshipRepository(
        justChattingApiService: JustChattingApiService,
        friendshipDao: FriendshipDao,
    ): FriendshipRepository {
        return DefaultFriendshipRepository(justChattingApiService, friendshipDao)
    }
}