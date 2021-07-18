package com.sungbin.sign_in_sign_up.login

import com.sungbin.sign_in_sign_up.data.LoginRequest
import com.sungbin.sign_in_sign_up.network.RetrofitClient

class LoginRepository {
    private val api = RetrofitClient.create()

    fun login(account: String, password: String) = api.login(LoginRequest(account, password))
}