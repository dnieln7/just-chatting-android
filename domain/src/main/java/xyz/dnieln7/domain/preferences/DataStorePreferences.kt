package xyz.dnieln7.domain.preferences

import kotlinx.coroutines.flow.Flow
import xyz.dnieln7.domain.model.User

interface DataStorePreferences {
    fun getUser(): Flow<User?>
    suspend fun setUser(user: User)
    suspend fun clear()
}