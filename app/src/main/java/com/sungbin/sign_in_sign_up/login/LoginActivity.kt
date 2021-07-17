package com.sungbin.sign_in_sign_up.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.sungbin.mypet.login.LoginViewModel
import com.sungbin.sign_in_sign_up.MainActivity
import com.sungbin.sign_in_sign_up.databinding.ActivityLoginBinding
import com.sungbin.sign_in_sign_up.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val TAG = LoginActivity::class.java.simpleName

    private val viewmmodel: LoginViewModel by viewModels()

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initBtnListener()

        viewmmodel.loginResult.observe(this, Observer { result ->
            Log.d(TAG, "LOGIN RESULT : ${result}")

            if (result) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            } else Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initBtnListener() {
        binding.run {
            loginBtn.setOnClickListener {
                viewmmodel.loginRequest(accountEdit.text.toString(), passwordEdit.text.toString())
            }
            registerBtn.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}