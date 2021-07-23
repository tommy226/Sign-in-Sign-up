package com.sungbin.sign_in_sign_up.network

import com.sungbin.sign_in_sign_up.data.*
import retrofit2.Call
import retrofit2.http.*

interface NetworkAPI {
    @PUT("auth/") // login
    fun login(@Body userinfo: LoginRequest): Call<LoginResponse>

    @POST("auth/authority")
    fun accountDup(@Body account: AccountCheckRequest): Call<AccountCheckResponse>

    @POST("auth/") // sign
    fun register(@Body registerinfo: RegisterRequest): Call<RegisterResponse>
}