package xyz.dnieln7.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.dnieln7.domain.model.User
import xyz.dnieln7.domain.preferences.DataStorePreferences

class DefaultDataStorePreferences(
    private val dataStore: DataStore<Preferences>,
) : DataStorePreferences {

    override suspend fun setUser(user: User) {
        dataStore.edit {
            it[USER_KEY] = user.toSingleLineString()
        }
    }

    override fun getUser(): Flow<User?> {
        return dataStore.data.withIOExceptionGuard().map {
            val singleLineString = it[USER_KEY] ?: DEFAULT_STRING

            User.fromSingleLineString(singleLineString)
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    companion object {

        fun build(context: Context): DefaultDataStorePreferences {
            return DefaultDataStorePreferences(dataStore = context.createDataStoreWithMigrations)
        }

        private const val PREFERENCES_NAME = "just_chatting_preferences"

        private val Context.createDataStoreWithMigrations by preferencesDataStore(PREFERENCES_NAME)
    }
}

private const val DEFAULT_STRING = ""
