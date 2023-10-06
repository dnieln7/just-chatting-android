package xyz.dnieln7.justchatting.di.common

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.dnieln7.justchatting.domain.provider.PreferencesProvider
import xyz.dnieln7.justchatting.domain.provider.ResourceProvider
import xyz.dnieln7.justchatting.framework.provider.DefaultResourceProvider
import xyz.dnieln7.justchatting.framework.provider.preferences.DefaultPreferencesProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return DefaultResourceProvider(context)
    }

    @Provides
    @Singleton
    fun providePreferencesProvider(@ApplicationContext context: Context): PreferencesProvider {
        return DefaultPreferencesProvider.build(context)
    }
}