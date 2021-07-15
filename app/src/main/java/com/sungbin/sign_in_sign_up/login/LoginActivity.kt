package com.sungbin.sign_in_sign_up.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.sungbin.mypet.login.LoginViewModel
import com.sungbin.sign_in_sign_up.R
import com.sungbin.sign_in_sign_up.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val TAG = LoginActivity::class.java.simpleName

    private val viewmmodel: LoginViewModel by viewModels()

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initLoginListener()

        viewmmodel.loginResult.observe(this, Observer {
            if(it) Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT)
            else Toast.makeText(this, "로그인 실패 입니다", Toast.LENGTH_SHORT)
        })
    }

    private fun initLoginListener() {
        binding.run {
            loginBtn.setOnClickListener {
                viewmmodel.loginRequest(accountEdit.text.toString(), passwordEdit.text.toString())
            }
        }
    }
}