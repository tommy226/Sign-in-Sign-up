package com.sungbin.mypet.network

import com.sungbin.mypet.data.LoginRequest
import com.sungbin.mypet.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkAPI {
    @POST("api/auth/login")
    fun login(@Body userinfo: LoginRequest): Call<LoginResponse>
}