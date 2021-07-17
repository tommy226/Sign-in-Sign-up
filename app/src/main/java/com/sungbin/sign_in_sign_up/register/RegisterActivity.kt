package com.sungbin.sign_in_sign_up.register

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sungbin.mypet.login.LoginViewModel
import com.sungbin.sign_in_sign_up.R
import com.sungbin.sign_in_sign_up.databinding.ActivityLoginBinding
import com.sungbin.sign_in_sign_up.databinding.ActivityRegisterBinding

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

        viewmmodel.accountcheck.observe(this, Observer {
            if(it) Toast.makeText(this, "사용 가능 한 아이디 입니다.", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "사용 불가능 한 아이디 입니다.", Toast.LENGTH_SHORT).show()
        })

        viewmmodel.inputPWcheck.observe(this, Observer { pwCheck ->
            if(pwCheck.equals(viewmmodel.inputPW.value)) Log.d(TAG, "사용가능")
            else Log.d(TAG, "사용 불가")
        })

        viewmmodel.cancelflag.observe(this, Observer {
            if(it) finish()
        })

    }
}