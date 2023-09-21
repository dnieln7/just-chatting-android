package xyz.dnieln7.justchatting.framework.extensions

import android.content.res.Configuration

fun Configuration.isPortrait(): Boolean {
    return orientation == Configuration.ORIENTATION_PORTRAIT
}
