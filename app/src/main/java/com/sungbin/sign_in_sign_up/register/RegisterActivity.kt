package com.sungbin.sign_in_sign_up.register

import android.content.Intent
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sungbin.sign_in_sign_up.MainActivity
import com.sungbin.sign_in_sign_up.R
import com.sungbin.sign_in_sign_up.databinding.ActivityLoginBinding
import com.sungbin.sign_in_sign_up.databinding.ActivityRegisterBinding
import com.sungbin.sign_in_sign_up.utils.EventObserver

class RegisterActivity : AppCompatActivity() {
    private val TAG = RegisterActivity::class.java.simpleName

    private val viewmmodel: RegisterViewModel by viewModels()

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.run {
            vm = viewmmodel
            lifecycleOwner = this@RegisterActivity
        }
        viewmmodel.toast.observe(this, EventObserver { message ->
           Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
        })

        viewmmodel.isPasswordAbled.observe(this, Observer {  pwCheck ->
            if(pwCheck) Log.d(TAG, "패스워드 사용 가능") else Log.d(TAG, "패스워드 사용 불가")
        })

        viewmmodel.cancelflag.observe(this, Observer {
            if(it) finish()
        })
    }
}