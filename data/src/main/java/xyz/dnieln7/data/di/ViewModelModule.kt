package xyz.dnieln7.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.data.websocket.DefaultSingleChatConnection
import xyz.dnieln7.domain.websocket.SingleChatConnection

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideSingleChatConnection(
        @IO dispatcher: CoroutineDispatcher,
        gson: Gson,
    ): SingleChatConnection {
        return DefaultSingleChatConnection(dispatcher, gson)
    }
}