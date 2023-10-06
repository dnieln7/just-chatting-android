package xyz.dnieln7.justchatting.framework.extension

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

fun Flow<Preferences>.withIOExceptionGuard(): Flow<Preferences> {
    return catch { throwable ->
        if (throwable is IOException) {
            emit(emptyPreferences())
        } else {
            throw throwable
        }
    }
}

