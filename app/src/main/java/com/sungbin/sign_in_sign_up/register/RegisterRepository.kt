package com.sungbin.sign_in_sign_up.register

import com.google.gson.Gson
import com.sungbin.sign_in_sign_up.data.RegisterRequest
import com.sungbin.sign_in_sign_up.network.RetrofitClient

class RegisterRepository {
    private val api = RetrofitClient.create()
    private val gson = Gson()

    fun accountDupCheck(account: String) = api.accountDup(gson.toJson(account))

    fun register(account: String, password: String, name: String) = api.register(RegisterRequest(account, password, name))
}