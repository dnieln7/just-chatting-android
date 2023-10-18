package xyz.dnieln7.data.server

import retrofit2.http.Body
import retrofit2.http.POST
import xyz.dnieln7.data.server.model.EmailAvailabilitySvModel
import xyz.dnieln7.data.server.model.LoginSvModel
import xyz.dnieln7.data.server.model.SignupSvModel
import xyz.dnieln7.data.server.model.UserSvModel

interface JustChattingApiService {

    @POST("login")
    suspend fun login(@Body body: LoginSvModel): UserSvModel

    @POST("signup")
    suspend fun signup(@Body body: SignupSvModel): UserSvModel

    @POST("email/availability")
    suspend fun getEmailAvailability(@Body body: EmailAvailabilitySvModel)
}
