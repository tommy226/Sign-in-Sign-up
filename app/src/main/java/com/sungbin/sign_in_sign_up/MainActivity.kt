package com.sungbin.sign_in_sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sungbin.sign_in_sign_up.databinding.ActivityMainBinding
import com.sungbin.sign_in_sign_up.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val account = MyApplication.prefs.getString("account","")
        val password = MyApplication.prefs.getString("password","")
        Log.d("MAIN", "USER INFO : $account and $password" )

        binding.logoutBtn.setOnClickListener {
            MyApplication.prefs.remove()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}