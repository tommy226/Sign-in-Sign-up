package com.sungbin.mypet.login

import com.sungbin.mypet.data.LoginRequest
import com.sungbin.mypet.network.RetrofitClient

class LoginRepository {
    private val api = RetrofitClient.create()

    fun login(account: String, password: String) = api.login(LoginRequest(account, password))
}