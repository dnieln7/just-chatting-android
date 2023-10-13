package xyz.dnieln7.justchatting.data.server.extension

import retrofit2.HttpException

fun HttpException.isConflict(): Boolean {
    return code() == HTTP_CONFLICT
}

private const val HTTP_CONFLICT = 409
