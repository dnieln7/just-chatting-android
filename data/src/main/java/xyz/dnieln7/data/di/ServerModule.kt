package xyz.dnieln7.data.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.dnieln7.data.BuildConfig
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.JustChattingClient
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
            .addCallAdapterFactory(EitherCallAdapterFactory.create())
            .build()
            .create(JustChattingApiService::class.java)
    }
}
