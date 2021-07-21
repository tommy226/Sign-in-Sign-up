package com.sungbin.sign_in_sign_up.login

import android.util.Log
import androidx.lifecycle.*
import com.sungbin.sign_in_sign_up.data.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private val TAG = LoginViewModel::class.java.simpleName
    private val repo = LoginRepository()

    private val _tokenModel = MutableLiveData<LoginResponse>()
    val tokenModel: LiveData<LoginResponse>
        get() = _tokenModel

    val inputAccount = MutableLiveData<String>("")
    val inputPW = MutableLiveData<String>("")

    private val _registerFlag = MutableLiveData<Boolean>()
    val registerFlag: LiveData<Boolean>
        get() = _registerFlag
    fun registerFlagDone() = _registerFlag.postValue(false)

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean>
        get() = _loginResult

    fun loginRequest(account: String, password: String)                                                     // 로그인 요청
            = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.login(account, password)
        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.code() == 200) {                               // 로그인 성공 시 accessToken, refreshToken 획득
                    val accessToken = response.headers()["accessToken"]
                    val refreshToken = response.headers()["refreshToken"]
                    Log.d(TAG, "MY accessToken : ${response.headers()["accessToken"]}" )
                    Log.d(TAG, "MY refreshToken : ${response.headers()["refreshToken"]}" )
                    _tokenModel.postValue(LoginResponse(accessToken = accessToken.toString(), refreshToken = refreshToken.toString()))

                    _loginResult.postValue(true)
                }  else _loginResult.postValue(false)
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