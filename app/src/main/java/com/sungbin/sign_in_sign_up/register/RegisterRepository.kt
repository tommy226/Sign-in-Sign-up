package com.sungbin.sign_in_sign_up.register

import com.sungbin.mypet.data.LoginRequest
import com.sungbin.mypet.network.RetrofitClient
import com.sungbin.sign_in_sign_up.data.RegisterRequest

class RegisterRepository {
    private val api = RetrofitClient.create()

    fun accountDupCheck(account: String) = api.accountDup(account)

    fun register(account: String, password: String, name: String) = api.register(RegisterRequest(account, password, name))
}