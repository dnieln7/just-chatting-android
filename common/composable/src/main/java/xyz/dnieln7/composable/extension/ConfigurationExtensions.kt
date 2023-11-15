package xyz.dnieln7.composable.extension

import android.content.res.Configuration

fun Configuration.isPortrait(): Boolean {
    return orientation == Configuration.ORIENTATION_PORTRAIT
}
