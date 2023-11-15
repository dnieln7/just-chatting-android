package xyz.dnieln7.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.dnieln7.data.preferences.DefaultDataStorePreferences
import xyz.dnieln7.domain.preferences.DataStorePreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStorePreferences {
        return DefaultDataStorePreferences.build(context)
    }
}