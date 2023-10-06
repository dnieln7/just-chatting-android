package xyz.dnieln7.justchatting.framework.provider

import android.content.Context
import androidx.annotation.StringRes
import xyz.dnieln7.justchatting.domain.provider.ResourceProvider

class DefaultResourceProvider(private val context: Context) : ResourceProvider {

    override fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    override fun getString(@StringRes id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }
}