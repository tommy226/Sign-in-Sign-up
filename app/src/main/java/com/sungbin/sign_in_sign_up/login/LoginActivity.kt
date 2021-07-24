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
import com.sungbin.sign_in_sign_up.MyApplication
import com.sungbin.sign_in_sign_up.R
import com.sungbin.sign_in_sign_up.databinding.ActivityLoginBinding
import com.sungbin.sign_in_sign_up.register.RegisterActivity
import com.sungbin.sign_in_sign_up.utils.showToast

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
        autoLogin()

        viewmmodel.tokenModel.observe(this, Observer { tokenModel ->
            Log.d(TAG, tokenModel.toString())                                                           // 서버에서 전달 받은 Token
            MyApplication.prefs.setString("access", tokenModel.accessToken)
            MyApplication.prefs.setString("refresh", tokenModel.refreshToken)
        })

        viewmmodel.loginResult.observe(this, Observer { result ->
            if (result) {
                showToast("로그인 성공")
                if (!viewmmodel.inputAccount.value.isNullOrEmpty()) {
                    MyApplication.prefs.setString("account", viewmmodel.inputAccount.value!!)      // 로그인 성공 시 자동로그인 아이디 등록
                    MyApplication.prefs.setString("password", viewmmodel.inputPW.value!!)
                }
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else showToast("로그인 실패")
        })

        viewmmodel.registerFlag.observe(
            this,
            Observer { result ->                                // 회원가입
                if (result) {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                    viewmmodel.registerFlagDone()
                }
        })
    }

    fun autoLogin() {
        if (MyApplication.prefs.getString("account", "").isNotEmpty()) {
            val account = MyApplication.prefs.getString("account", "")
            val password = MyApplication.prefs.getString("password", "")
            viewmmodel.loginRequest(account, password)
        }
    }

    override fun onRestart() {
        super.onRestart()
        autoLogin()
    }
}