package com.sungbin.sign_in_sign_up

import android.app.Application
import com.sungbin.sign_in_sign_up.utils.PreferenceUtil

class MyApplication : Application() {
    companion object{
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}