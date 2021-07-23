package com.sungbin.sign_in_sign_up.register

import android.content.Intent
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sungbin.sign_in_sign_up.MainActivity
import com.sungbin.sign_in_sign_up.MyApplication
import com.sungbin.sign_in_sign_up.R
import com.sungbin.sign_in_sign_up.databinding.ActivityLoginBinding
import com.sungbin.sign_in_sign_up.databinding.ActivityRegisterBinding
import com.sungbin.sign_in_sign_up.utils.EventObserver
import com.sungbin.sign_in_sign_up.utils.Patterns
import com.sungbin.sign_in_sign_up.utils.showToast

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
           showToast(message)
        })

        viewmmodel.isPasswordAbled.observe(this, Observer {  pwCheck ->
            if(pwCheck) Log.d(TAG, "패스워드 사용 가능") else Log.d(TAG, "패스워드 사용 불가")
        })

        viewmmodel.isCancel.observe(this, Observer { isCancel ->
            if(isCancel) finish()
        })

        viewmmodel.isRegister.observe(this, Observer { isRegister ->
            if(isRegister){
                MyApplication.prefs.setString("account", viewmmodel.inputAccount.value!!)           // 로그인 성공 시 자동로그인 아이디 등록
                MyApplication.prefs.setString("password", viewmmodel.inputPW.value!!)
                finish()
            }
        })

        viewmmodel.inputPW.observe(this, Observer { password ->         // 패스워드 조건 정규식
            val pattern = Patterns.pPattern.matcher(password)
            if (!password.isNullOrBlank()) {
                if (pattern.matches()) {
                    binding.passwordRightText.visibility = View.VISIBLE
                    binding.passwordErrorText.visibility = View.GONE
                } else {
                    binding.passwordRightText.visibility = View.GONE
                    binding.passwordErrorText.visibility = View.VISIBLE
                }
            } else {
                binding.passwordRightText.visibility = View.GONE
                binding.passwordErrorText.visibility = View.GONE
            }
        })
    }
}