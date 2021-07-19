package com.sungbin.sign_in_sign_up.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sungbin.sign_in_sign_up.MainActivity
import com.sungbin.sign_in_sign_up.R
import com.sungbin.sign_in_sign_up.databinding.ActivityLoginBinding
import com.sungbin.sign_in_sign_up.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private val TAG = LoginActivity::class.java.simpleName

    private val viewmmodel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.run {
            vm = viewmmodel
            lifecycleOwner = this@LoginActivity
        }
        viewmmodel.loginResult.observe(this, Observer { result ->
            Log.d(TAG, "LOGIN RESULT : ${result}")

            if (result) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            } else Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
        })

        viewmmodel.registerFlag.observe(this, Observer { result ->
            if(result){
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                viewmmodel.registerFlagDone()
            }
        })
    }
}