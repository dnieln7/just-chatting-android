package xyz.dnieln7.data.fake

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

fun buildHttpException(code: Int): HttpException {
    return HttpException(Response.error<Void>(code, "".toResponseBody()))
}
