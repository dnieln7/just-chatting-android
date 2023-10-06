package xyz.dnieln7.justchatting.di.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.dnieln7.justchatting.BuildConfig
import xyz.dnieln7.justchatting.data.server.JustChattingApiService
import xyz.dnieln7.justchatting.data.server.JustChattingClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {

    @Provides
    @Singleton
    fun provideJustChattingClient(): JustChattingClient {
        return JustChattingClient()
    }

    @Provides
    @Singleton
    fun provideJustChattingApiService(justChattingClient: JustChattingClient): JustChattingApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.JUST_CHATTING_URL)
            .client(justChattingClient.okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(JustChattingApiService::class.java)
    }
}
