package xyz.dnieln7.justchatting.framework.provider.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.dnieln7.justchatting.domain.model.User
import xyz.dnieln7.justchatting.domain.provider.PreferencesProvider
import xyz.dnieln7.justchatting.framework.extension.withIOExceptionGuard

class DefaultPreferencesProvider(
    private val dataStore: DataStore<Preferences>,
) : PreferencesProvider {

    override suspend fun setUser(user: User) {
        dataStore.edit {
            it[PreferencesKeys.USER] = user.toSingleLineString()
        }
    }

    override fun getUser(): Flow<User?> {
        return dataStore.data.withIOExceptionGuard().map {
            val singleLineString = it[PreferencesKeys.USER] ?: DEFAULT_STRING

            User.fromSingleLineString(singleLineString)
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    companion object {

        fun build(context: Context): DefaultPreferencesProvider {
            return DefaultPreferencesProvider(dataStore = context.createDataStoreWithMigrations)
        }

        private const val PREFERENCES_NAME = "just_chatting_preferences"

        private val Context.createDataStoreWithMigrations by preferencesDataStore(PREFERENCES_NAME)
    }
}

private const val DEFAULT_STRING = ""
        