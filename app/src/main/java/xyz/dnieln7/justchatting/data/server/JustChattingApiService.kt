package xyz.dnieln7.justchatting.data.server

import retrofit2.http.Body
import retrofit2.http.POST
import xyz.dnieln7.justchatting.data.server.model.LoginSvModel
import xyz.dnieln7.justchatting.data.server.model.UserSvModel

interface JustChattingApiService {

    @POST("login")
    suspend fun login(@Body body: LoginSvModel): UserSvModel
}
