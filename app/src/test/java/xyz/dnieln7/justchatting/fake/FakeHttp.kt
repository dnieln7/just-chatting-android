package xyz.dnieln7.justchatting.fake

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

fun buildHttpException(code: Int, message: String = "Internal server error"): HttpException {
    return HttpException(Response.error<Void>(code, message.toResponseBody()))
}
