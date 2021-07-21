package com.sungbin.sign_in_sign_up.register

import android.util.Log
import androidx.lifecycle.*
import com.sungbin.sign_in_sign_up.data.AccountCheckResponse
import com.sungbin.sign_in_sign_up.data.RegisterResponse
import com.sungbin.sign_in_sign_up.utils.Event
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

    private var isAccountAbled: Boolean? = false

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>>
        get() = _toast

    private val _cancelflag = MutableLiveData<Boolean>()
    val cancelflag: LiveData<Boolean>
        get() = _cancelflag
    fun cancel() = _cancelflag.postValue(true)   // 회원가입 취소 버튼

    fun accountDuplicated() =
        viewModelScope.launch(Dispatchers.IO) {                                        // 아이디 중복 확인
            val pattern = android.util.Patterns.EMAIL_ADDRESS
            val emailpattern = inputAccount.value?.let { pattern.matcher(it).matches() }
            if (emailpattern == true) {
                val response = inputAccount.value?.let { repo.accountDupCheck(it) }
                response?.enqueue(object : Callback<AccountCheckResponse> {
                    override fun onResponse(
                        call: Call<AccountCheckResponse>,
                        response: Response<AccountCheckResponse>
                    ) {
                        Log.d(TAG, "RESPONSE CODE : ${response.code()}")

                        if (response.code() == 200) {
                            isAccountAbled = true
                            _toast.postValue(Event("사용 가능한 아이디 입니다."))
                        }
                        else if(response.code() == 202){
                            isAccountAbled = false
                            _toast.postValue(Event("중복 된 아이디 입니다."))
                        }
                        else {
                            isAccountAbled = false
                            _toast.postValue(Event("사용 불가능한 아이디 입니다."))
                        }
                    }

                    override fun onFailure(call: Call<AccountCheckResponse>, t: Throwable) {

                    }
                })
            } else {
                _toast.postValue(Event("이메일 형식이 아닙니다."))
            }
        }

    fun registerRequest(account: String, password: String, name: String) = viewModelScope.launch(Dispatchers.IO) {      // 회원가입 요청
            if (blankCheck() && isPasswordAbled.value == true && isAccountAbled == true) {
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
                _toast.value = Event("조건에 맞지 않습니다 다시 확인 해주세요")    // 클라이언트 입장에서 회원가입 요청 조건이 모두 맞는지 확인
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