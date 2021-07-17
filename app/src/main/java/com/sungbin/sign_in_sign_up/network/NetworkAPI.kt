package com.sungbin.mypet.network

import com.sungbin.mypet.data.LoginRequest
import com.sungbin.mypet.data.LoginResponse
import com.sungbin.sign_in_sign_up.data.RegisterRequest
import com.sungbin.sign_in_sign_up.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface NetworkAPI {
    @POST("api/auth/login")
    fun login(@Body userinfo: LoginRequest): Call<LoginResponse>

    @GET("api/auth/account/{id}")
    fun accountDup(@Path("id") account: String): Call<String>

    @POST("api/auth/register")
    fun register(@Body registerinfo: RegisterRequest): Call<RegisterResponse>
}