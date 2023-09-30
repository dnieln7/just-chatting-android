package xyz.dnieln7.justchatting.di.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Main

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IO

@InstallIn(SingletonComponent::class)
@Module
object CoroutineDispatcherModule {

    @Main
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @IO
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}
