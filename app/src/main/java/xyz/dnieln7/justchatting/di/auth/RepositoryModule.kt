package xyz.dnieln7.justchatting.di.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.dnieln7.justchatting.data.repository.DefaultAuthRepository
import xyz.dnieln7.justchatting.data.server.JustChattingApiService
import xyz.dnieln7.justchatting.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(justChattingApiService: JustChattingApiService): AuthRepository {
        return DefaultAuthRepository(justChattingApiService)
    }
}
