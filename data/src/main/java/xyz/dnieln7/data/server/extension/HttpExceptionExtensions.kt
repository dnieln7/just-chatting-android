package xyz.dnieln7.data.server.extension

import retrofit2.HttpException

fun HttpException.isConflict(): Boolean {
    return code() == HTTP_CONFLICT
}

fun HttpException.isNotFound(): Boolean {
    return code() == HTTP_NOT_FOUND
}

private const val HTTP_CONFLICT = 409
private const val HTTP_NOT_FOUND = 400
