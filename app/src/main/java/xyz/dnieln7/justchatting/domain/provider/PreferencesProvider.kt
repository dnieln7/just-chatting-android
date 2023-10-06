package xyz.dnieln7.justchatting.domain.provider

import kotlinx.coroutines.flow.Flow
import xyz.dnieln7.justchatting.domain.model.User

interface PreferencesProvider {

    fun getUser(): Flow<User?>
    suspend fun setUser(user: User)

    suspend fun clear()
}