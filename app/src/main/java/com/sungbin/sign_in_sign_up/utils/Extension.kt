package com.sungbin.sign_in_sign_up.utils

import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// retrofit 통신 확장 함수
fun<T> Call<T>.customEnqueue(
    onSuccess : (Response<T>) -> Unit,
    onError : (Response<T>) -> Unit = {},
    onFailure  : () -> Unit = {}
){
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e("Server Fail", "Server Closed")
            t.printStackTrace()
            onFailure()
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.takeIf { it.isSuccessful }
                ?.body()
                ?.let{
                    onSuccess(response)
                } ?: onError(response)
        }
    })
}