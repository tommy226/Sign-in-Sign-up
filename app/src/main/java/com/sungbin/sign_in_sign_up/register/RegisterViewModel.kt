package com.sungbin.sign_in_sign_up.register

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.sign_in_sign_up.data.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel(){
    private val TAG = RegisterViewModel::class.java.simpleName
    private val repo = RegisterRepository()

    val _inputAccount = MutableLiveData<String>()
    val inputAccount: LiveData<String>
        get() = _inputAccount

    val _inputPW = MutableLiveData<String>()
    val inputPW: LiveData<String>
        get() = _inputPW

    val _inputPWcheck = MutableLiveData<String>()
    val inputPWcheck: LiveData<String>
        get() = _inputPWcheck

    val _inputName = MutableLiveData<String>()
    val inputName: LiveData<String>
        get() = _inputName

    private val _accountcheck = MutableLiveData<Boolean>()
    val accountcheck: LiveData<Boolean>
        get() = _accountcheck

    private val _cancelflag = MutableLiveData<Boolean>()
    val cancelflag: LiveData<Boolean>
        get() = _cancelflag
    fun cancel() = _cancelflag.postValue(true)

    fun accountDuplicated() {
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

    private val _registerError = MutableLiveData<Boolean>()
    val registerError: LiveData<Boolean>
        get() = _registerError
    fun registerErrorDone() = _registerError.postValue(false)

    fun registerRequest(){
        if(nullcheck() && pwcheck() && accountcheck.value!!){
            val response = repo.register(inputAccount.value!!, inputPW.value!!, inputName.value!!)
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
        }else{
            _registerError.postValue(true)
        }
    }

    fun nullcheck(): Boolean{
        return !(inputAccount.value == null || inputPW.value == null || inputPWcheck.value == null || inputName.value == null)
    }

    fun pwcheck(): Boolean{
        return inputPW.value!!.equals(inputPWcheck.value!!)
    }
}