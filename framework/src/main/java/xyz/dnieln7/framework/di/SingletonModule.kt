package xyz.dnieln7.framework.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.framework.provider.DefaultResourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return DefaultResourceProvider(context)
    }
}