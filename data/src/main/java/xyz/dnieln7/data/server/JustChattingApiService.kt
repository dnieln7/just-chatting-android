package xyz.dnieln7.data.server

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xyz.dnieln7.data.server.model.AcceptFriendshipRequestSvModel
import xyz.dnieln7.data.server.model.ChatSvModel
import xyz.dnieln7.data.server.model.CreateChatSvModel
import xyz.dnieln7.data.server.model.EmailAvailabilitySvModel
import xyz.dnieln7.data.server.model.FriendshipSvModel
import xyz.dnieln7.data.server.model.GetChatsSvModel
import xyz.dnieln7.data.server.model.GetMessagesEagerSvModel
import xyz.dnieln7.data.server.model.LoginSvModel
import xyz.dnieln7.data.server.model.SendFriendshipRequestSvModel
import xyz.dnieln7.data.server.model.SendMessageSvModel
import xyz.dnieln7.data.server.model.SignupSvModel
import xyz.dnieln7.data.server.model.UserSvModel

interface JustChattingApiService {

    @POST("login")
    suspend fun login(@Body body: LoginSvModel): UserSvModel

    @POST("signup")
    suspend fun signup(@Body body: SignupSvModel): UserSvModel

    @POST("email/availability")
    suspend fun getEmailAvailability(@Body body: EmailAvailabilitySvModel): Either<CallError, Unit>

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): UserSvModel

    @POST("friendships")
    suspend fun sendFriendshipRequest(@Body body: SendFriendshipRequestSvModel)

    @POST("accept-friendship")
    suspend fun acceptFriendshipRequest(@Body body: AcceptFriendshipRequestSvModel)

    @GET("users/{id}/pending-friendships")
    suspend fun getPendingFriendships(@Path("id") userID: String): List<FriendshipSvModel>

    @GET("users/{id}/friendships")
    suspend fun getFriendships(@Path("id") userID: String): List<FriendshipSvModel>

    @DELETE("friendships/{user_id}/{friend_id}")
    suspend fun deleteFriendship(
        @Path("user_id") userID: String,
        @Path("friend_id") friendID: String,
    )

    @GET("users/{id}/chats")
    suspend fun getChats(@Path("id") userID: String): GetChatsSvModel

    @GET("users/{user_id}/chats/{chat_id}")
    suspend fun getChat(
        @Path("user_id") userID: String,
        @Path("chat_id") chatID: String,
    ): ChatSvModel

    @POST("chats")
    suspend fun createChat(@Body body: CreateChatSvModel): ChatSvModel

    @GET("eager/chats/{id}/messages")
    suspend fun getMessagesEager(@Path("id") chatID: String): GetMessagesEagerSvModel

    @POST("messages")
    suspend fun sendMessage(
        @Body body: SendMessageSvModel,
    )
}
