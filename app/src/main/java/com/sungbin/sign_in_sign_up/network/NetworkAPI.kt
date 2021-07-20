package com.sungbin.sign_in_sign_up.network

import com.sungbin.sign_in_sign_up.data.LoginRequest
import com.sungbin.sign_in_sign_up.data.LoginResponse
import com.sungbin.sign_in_sign_up.data.RegisterRequest
import com.sungbin.sign_in_sign_up.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface NetworkAPI {
    @POST("api/auth/login")
    suspend fun login(@Body userinfo: LoginRequest): Call<LoginResponse>

    @GET("api/auth/account/{id}")
    suspend fun accountDup(@Path("id") account: String): Call<String>

    @POST("api/auth/register")
    suspend fun register(@Body registerinfo: RegisterRequest): Call<RegisterResponse>
}