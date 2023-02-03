package com.example.petsafe2.model.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("/login")
    fun getLoginCheck(
        @Body user: UserRetrofitBody
    ): Call<UserRetrofitResult>

}

data class UserRetrofitBody(
    val userId: String,
    val userPwd: String
)

data class UserRetrofitResult(
    val message: String
)


