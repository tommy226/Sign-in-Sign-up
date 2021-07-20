package com.sungbin.sign_in_sign_up.register

import androidx.lifecycle.*
import com.sungbin.sign_in_sign_up.data.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel(){
    private val TAG = RegisterViewModel::class.java.simpleName
    private val repo = RegisterRepository()

    val inputAccount = MutableLiveData<String>()
    val inputPW = MutableLiveData<String>()
    val inputPWcheck = MutableLiveData<String>()
    val inputName = MutableLiveData<String>()

    private val _accountcheck = MutableLiveData<Boolean>()
    val accountcheck: LiveData<Boolean>
        get() = _accountcheck

    private val _cancelflag = MutableLiveData<Boolean>()
    val cancelflag: LiveData<Boolean>
        get() = _cancelflag
    fun cancel() = _cancelflag.postValue(true)   // 회원가입 취소 버튼

    fun accountDuplicated() =
        viewModelScope.launch(Dispatchers.IO) {                                        // 아이디 중복 확인
            val response = inputAccount.value?.let { repo.accountDupCheck(it) }
            response?.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.code() == 200) _accountcheck.postValue(true)
                    else _accountcheck.postValue(false)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _accountcheck.postValue(false)
                }
            })
        }


    private val _registerError = MutableLiveData<Boolean>()             // 클라이언트 입장에서 회원가입 요청 조건이 모두 맞는지 확인
    val registerError: LiveData<Boolean>
        get() = _registerError
    fun registerErrorDone() = _registerError.postValue(false)

    fun registerRequest(account: String, password: String, name: String) = viewModelScope.launch(Dispatchers.IO) {      // 회원가입 요청
            if (blankCheck() && isPasswordAbled.value == true && accountcheck.value == true) {

                val response = repo.register(account, password, name)
                response.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.code() == 200) {
                            // 회원가입 완료 시 처리
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                    }
                })
            } else {
                _registerError.postValue(true)
            }
        }

    private fun blankCheck() = !(inputAccount.value.isNullOrBlank()     // 회원가입 EditText Null 여부
            || inputPW.value.isNullOrEmpty()
            || inputPWcheck.value.isNullOrEmpty()
            || inputName.value.isNullOrBlank())

    private val _isPasswordAbled = MediatorLiveData<Boolean>().apply { // 비밀번호 동일한지 확인
        addSource(inputPW){ value = pwCheck() }
        addSource(inputPWcheck){ value = pwCheck() }
    }
    val isPasswordAbled: LiveData<Boolean>
        get() = _isPasswordAbled
    private fun pwCheck() = inputPW.value.equals(inputPWcheck.value)
}