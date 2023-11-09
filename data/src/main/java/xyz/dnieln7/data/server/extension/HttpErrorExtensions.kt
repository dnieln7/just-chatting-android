package xyz.dnieln7.data.server.extension

import arrow.retrofit.adapter.either.networkhandling.HttpError

fun HttpError.isConflict(): Boolean {
    return code == HTTP_CONFLICT
}

private const val HTTP_CONFLICT = 409
