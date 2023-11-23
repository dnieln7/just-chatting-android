package xyz.dnieln7.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.dnieln7.data.repository.DefaultAuthRepository
import xyz.dnieln7.data.repository.DefaultChatRepository
import xyz.dnieln7.data.repository.DefaultFriendshipRepository
import xyz.dnieln7.data.repository.DefaultMessageRepository
import xyz.dnieln7.data.repository.DefaultUserRepository
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.domain.repository.AuthRepository
import xyz.dnieln7.domain.repository.ChatRepository
import xyz.dnieln7.domain.repository.FriendshipRepository
import xyz.dnieln7.domain.repository.MessageRepository
import xyz.dnieln7.domain.repository.UserRepository
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
        resourceProvider: ResourceProvider,
    ): FriendshipRepository {
        return DefaultFriendshipRepository(justChattingApiService, resourceProvider)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        justChattingApiService: JustChattingApiService,
        resourceProvider: ResourceProvider,
    ): UserRepository {
        return DefaultUserRepository(justChattingApiService, resourceProvider)
    }

    @Provides
    @Singleton
    fun provideChatRepository(justChattingApiService: JustChattingApiService): ChatRepository {
        return DefaultChatRepository(justChattingApiService)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(justChattingApiService: JustChattingApiService): MessageRepository {
        return DefaultMessageRepository(justChattingApiService)
    }
}
