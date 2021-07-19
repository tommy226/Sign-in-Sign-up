package com.sungbin.sign_in_sign_up.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.sign_in_sign_up.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private val TAG = LoginViewModel::class.java.simpleName
    private val repo = LoginRepository()

    val _inputAccount = MutableLiveData<String>()
    val inputAccount: LiveData<String>
        get() = _inputAccount

    val _inputPW = MutableLiveData<String>()
    val inputPW: LiveData<String>
        get() = _inputPW

    private val _registerFlag = MutableLiveData<Boolean>()
    val registerFlag: LiveData<Boolean>
        get() = _registerFlag
    fun registerFlagDone() = _registerFlag.postValue(false)

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult

    fun loginRequest() {                                                        // 로그인 요청
        val response = repo.login(inputAccount.value!!, inputPW.value!!)
        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.code() == 200) _loginResult.postValue(true) else _loginResult.postValue(
                    false
                )
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.postValue(false)
            }
        })
    }

    fun callRegister() = _registerFlag.postValue(true)

    private val _isEndalbedLogin = MediatorLiveData<Boolean>().apply {
        addSource(inputAccount){ value = isValidEnterInfo() }
        addSource(inputPW){ value = isValidEnterInfo() }
    }
    val isEndalbedLogin: LiveData<Boolean>
        get() = _isEndalbedLogin

    private fun isValidEnterInfo() = !inputAccount.value.isNullOrBlank() && !inputPW.value.isNullOrEmpty()
                                    // 아이디, 비밀번호가 모두 입력 되었을 시 버튼 활성화
}